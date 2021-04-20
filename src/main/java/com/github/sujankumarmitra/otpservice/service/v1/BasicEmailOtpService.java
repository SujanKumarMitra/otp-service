package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.OtpProperties;
import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.exception.v1.*;
import com.github.sujankumarmitra.otpservice.model.v1.*;
import com.github.sujankumarmitra.otpservice.util.v1.OtpCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.sujankumarmitra.otpservice.model.v1.OtpStatus.*;

@Service
public class BasicEmailOtpService implements EmailOtpService {

    private OtpCodeGenerator codeGenerator;
    private OtpProperties otpProperties;
    private EmailOtpDao otpDao;
    private OtpStatusDetailsDao otpStatusDetailsDao;
    private EmailOtpProcessor emailOtpProcessor;
    private ExecutorService executorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicEmailOtpService.class);

    @Autowired
    public BasicEmailOtpService(OtpCodeGenerator codeGenerator,
                                OtpProperties otpProperties,
                                EmailOtpDao otpDao,
                                OtpStatusDetailsDao otpStatusDetailsDao,
                                EmailOtpProcessor emailOtpProcessor) {
        this.codeGenerator = codeGenerator;
        this.otpProperties = otpProperties;
        this.otpDao = otpDao;
        this.otpStatusDetailsDao = otpStatusDetailsDao;
        this.emailOtpProcessor = emailOtpProcessor;

        this.executorService = Executors.newWorkStealingPool();
    }

    @Override
    public CreateOtpResponse createOtp(CreateEmailOtpRequest request) throws OtpCreationException {
        EmailOtp emailOtp = buildEmailOtp(request);
        LOGGER.info("Creating EmailOtp with id = {}", emailOtp.getId());
        try {
            otpDao.save(emailOtp);
        } catch (OtpAlreadyExistsException e) {
            throw new OtpCreationException(e.getMessage());
        }

        OtpStatusDetails otpStatusDetails = buildOtpStatusDetails(emailOtp);
        try {
            otpStatusDetailsDao.insertStatusDetails(otpStatusDetails);
        } catch (OtpStatusDetailsAlreadyExistsException | OtpNotFoundException e) {
            throw new OtpCreationException(e.getMessage());
        }

        try {
            executorService.submit(() -> emailOtpProcessor.processOtp(emailOtp));
        } catch (Exception e) {
            throw new OtpCreationException(e.getMessage());
        }

        return new JacksonCompatibleCreateOtpResponse(emailOtp.getId());
    }

    private OtpStatusDetails buildOtpStatusDetails(EmailOtp emailOtp) {
        return BasicOtpStatusDetails.newBuilder()
                .withOtpId(emailOtp.getId())
                .withCurrentStatus(OtpStatus.NEW)
                .withCurrentStatusReasonPhrase("OtpCreateRequest is accepted.")
                .withTotalVerificationAttemptsMade(0L)
                .build();
    }

    private EmailOtp buildEmailOtp(CreateEmailOtpRequest request) {
        final String otpId = UUID.randomUUID().toString();
        final String otpCode = codeGenerator.generateNewOtpCode();
        final String messageTemplate = request.getMessageTemplate() == null ?
                otpProperties.getDefaultMessageTemplate() :
                request.getMessageTemplate();
        return BasicEmailOtp.newBuilder()
                .withId(otpId)
                .withCode(otpCode)
                .withEmailAddress(request.getEmailAddress())
                .withCreatedAt(Instant.ofEpochMilli(0))
                .withDurationBeforeExpiry(Duration.ofSeconds(request.getExpiryTimeInSeconds()))
                .withMessageBody(messageTemplate)
                .build();
    }

    @Override
    public OtpVerificationResponse verifyOtp(OtpVerificationRequest request) throws OtpVerificationException, OtpNotFoundException {
        final OtpStatus status;
        final boolean verified;
        final String message;
        final long attemptsRemaining;

        LOGGER.info("Verifying Otp of id={}", request.getOtpId());
        final OtpStatusDetails otpStatusDetails = getOtpStatusDetails(request);
        final EmailOtp otp = getOtp(request);
        if (isAlreadyVerified(otpStatusDetails)) {
            verified = true;
            message = "Otp is already being verified";
            status = VERIFIED;
            attemptsRemaining = 0L;
        } else if (isOtpInvalid(otpStatusDetails)) {
            verified = false;
            message = "Too many verification attempts";
            status = INVALID;
            attemptsRemaining = 0L;
        } else if (isOtpExpired(otp, otpStatusDetails)) {
            verified = false;
            message = "Otp has expired";
            status = EXPIRED;
            attemptsRemaining = 0L;
        } else if (verify(request, otp)) {
            verified = true;
            message = "Otp is verified";
            status = VERIFIED;
            attemptsRemaining = 0L;
            otpStatusDetailsDao.setStatus(
                    otp.getId(),
                    status,
                    message);
        } else {
            verified = false;
            final long allowedAttempts = otpProperties.getTotalAllowedAttempts();
            final long attemptsMade = otpStatusDetails.getTotalVerificationAttemptsMade() + 1;
            attemptsRemaining = allowedAttempts - attemptsMade;
            if (attemptsRemaining == 0) {
                message = "Too many incorrect verification attempts. Otp is now Invalid";
                status = INVALID;
                otpStatusDetailsDao
                        .setStatus(
                                otp.getId(),
                                INVALID,
                                "Too many incorrect verification attempts");
            } else {
                message = "Incorrect OtpCode";
                status = WAITING_FOR_VERIFICATION;
                otpStatusDetailsDao
                        .setTotalVerificationAttemptsMade(otp.getId(), attemptsMade);
            }
        }

        LOGGER.info("Verification result of OtpId={}, verified={}, status={}", request.getOtpId(),verified, status);

        return new JacksonCompatibleOtpVerificationResponse(
                verified,
                status,
                message,
                attemptsRemaining);
    }

    private boolean isAlreadyVerified(OtpStatusDetails statusDetails) {
        return statusDetails.getCurrentStatus().equals(VERIFIED);
    }

    private boolean isOtpInvalid(OtpStatusDetails statusDetails) {
        return statusDetails.getCurrentStatus().equals(INVALID);
    }

    private boolean isOtpExpired(EmailOtp otp, OtpStatusDetails statusDetails) {
        boolean expired = false;
        if (statusDetails.getCurrentStatus().equals(EXPIRED)) {
            expired = true;
        } else {
            Instant now = Instant.now();
            Instant createdAt = otp.getCreatedAt();
            Instant instantOfExpiry = createdAt.plus(otp.getDurationBeforeExpiry());

            if (now.isAfter(instantOfExpiry)) {
                expired = true;
                otpStatusDetailsDao
                        .setStatus(otp.getId(),
                                EXPIRED,
                                "Otp wasn't verified within supplied duration");
            }
        }
        return expired;
    }

    private boolean verify(OtpVerificationRequest request, EmailOtp otp) {
        final String suppliedOtpCode = request.getOtpCode();
        final String actualOtpCode = otp.getCode();
        return suppliedOtpCode.contentEquals(actualOtpCode);
    }

    private OtpStatusDetails getOtpStatusDetails(OtpVerificationRequest request) {
        String otpId = request.getOtpId();
        return otpStatusDetailsDao
                .getStatusDetails(otpId)
                .orElseThrow(() -> new OtpNotFoundException(otpId));
    }

    private EmailOtp getOtp(OtpVerificationRequest request) {
        String otpId = request.getOtpId();
        return otpDao.getOtp(otpId)
                .orElseThrow(() -> new OtpNotFoundException(otpId));
    }

    @Override
    public OtpStatusDetails getCurrentOtpStatus(String otpId) throws OtpNotFoundException {
        return otpStatusDetailsDao.getStatusDetails(otpId)
                .orElseThrow(() -> new OtpNotFoundException(otpId));
    }

    @PreDestroy
    protected void preDestroy() {
        executorService.shutdown();
    }

    protected void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
