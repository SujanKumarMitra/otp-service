package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.OtpProperties;
import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.exception.v1.*;
import com.github.sujankumarmitra.otpservice.model.v1.*;
import com.github.sujankumarmitra.otpservice.util.v1.OtpCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BasicEmailOtpService implements EmailOtpService {

    private OtpCodeGenerator codeGenerator;
    private OtpProperties properties;
    private EmailOtpDao otpDao;
    private OtpStatusDetailsDao otpStatusDetailsDao;
    private EmailOtpProcessor emailOtpProcessor;
    private ExecutorService executorService;

    @Autowired
    public BasicEmailOtpService(OtpCodeGenerator codeGenerator,
                                OtpProperties properties,
                                EmailOtpDao otpDao,
                                OtpStatusDetailsDao otpStatusDetailsDao,
                                EmailOtpProcessor emailOtpProcessor) {
        this.codeGenerator = codeGenerator;
        this.properties = properties;
        this.otpDao = otpDao;
        this.otpStatusDetailsDao = otpStatusDetailsDao;
        this.emailOtpProcessor = emailOtpProcessor;

        this.executorService = Executors.newWorkStealingPool();
    }

    @Override
    public CreateOtpResponse createOtp(CreateEmailOtpRequest request) throws OtpCreationException {
        EmailOtp emailOtp = buildEmailOtp(request);
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
                properties.getDefaultMessageTemplate() :
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
        return null;
    }

    @Override
    public OtpStatusDetails getCurrentOtpStatus(String otpId) throws OtpNotFoundException {
        return otpStatusDetailsDao.getStatusDetails(otpId)
                .orElseThrow(()-> new OtpNotFoundException(otpId));
    }

    @PreDestroy
    protected void preDestroy() {
        executorService.shutdown();
    }

    protected void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
