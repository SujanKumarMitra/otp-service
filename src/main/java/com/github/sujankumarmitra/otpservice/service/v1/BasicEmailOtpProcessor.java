package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.EmailProperties;
import com.github.sujankumarmitra.otpservice.configuration.v1.OtpProperties;
import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStatusDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.BasicEmailMessage;
import com.github.sujankumarmitra.otpservice.model.v1.EmailMessage;
import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BasicEmailOtpProcessor implements EmailOtpProcessor {
    private OtpStatusDetailsDao otpStatusDetailsDao;
    private EmailOtpDao emailOtpDao;
    private EmailService emailService;
    private OtpProperties otpProperties;
    private EmailProperties emailProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicEmailOtpProcessor.class);

    @Autowired
    public BasicEmailOtpProcessor(OtpStatusDetailsDao otpStatusDetailsDao,
                                  EmailOtpDao emailOtpDao,
                                  EmailService emailService,
                                  OtpProperties otpProperties,
                                  EmailProperties emailProperties) {
        this.otpStatusDetailsDao = otpStatusDetailsDao;
        this.emailOtpDao = emailOtpDao;
        this.emailService = emailService;
        this.otpProperties = otpProperties;
        this.emailProperties = emailProperties;
    }

    @Override
    public void processOtp(EmailOtp emailOtp) {
      /* job processor has picked the job
        So, set the current status to processing */
        LOGGER.info("Process EmailOtp with id={}",emailOtp.getId());
        setStatusToProcessing(emailOtp);

        EmailMessage message = buildEmailMessage(emailOtp);
        try {
//            send email
            LOGGER.info("Sending email with otp");
            emailService.sendEmail(message);
        } catch (EmailMessagingException e) {
          /*otp could not be sent.
            So, set status to processing error */
            LOGGER.warn("Could not send otp of id={}, and email={}", emailOtp.getId(), emailOtp.getEmailAddress());
            setStatusToProcessingError(emailOtp);
            return;
        }

        LOGGER.info("EmailOtp with id={} is sent successfully to {}", emailOtp.getId(), emailOtp.getEmailAddress());
        /*
        Otp is successfully sent to user.
        So update created at to current time, to merge the delay of
        OtpCreateRequest submission and processing
        */
        updateCreatedAt(emailOtp);
        /*
        Otp is sent to user.
        So, update current status to waiting for verification
        */
        setStatusToWaitingForVerification(emailOtp);
    }

    private void setStatusToProcessingError(EmailOtp emailOtp) {
        otpStatusDetailsDao.setStatus(emailOtp.getId(), OtpStatus.PROCESSING_ERROR, "Error occurred during processing OtpCreateRequest. Email couldn't be sent.");
    }

    private void updateCreatedAt(EmailOtp emailOtp) {
        Instant now = Instant.now();
        try {
            emailOtpDao.setCreatedAt(emailOtp.getId(), now);
        } catch (OtpNotFoundException e) {
            LOGGER.warn("", e);
        }
    }

    private EmailMessage buildEmailMessage(EmailOtp emailOtp) {
        final String messageBody = emailOtp.getMessageBody();
        final String regex = otpProperties.getMessageTemplateCodePlaceholderRegex();
        final String emailBody = messageBody.replaceAll(regex, emailOtp.getCode());

        return BasicEmailMessage.newBuilder()
                .withBody(emailBody)
                .withRecipient(emailOtp.getEmailAddress())
                .withSubject(emailProperties.getEmailSubject())
                .build();
    }

    private void setStatusToWaitingForVerification(EmailOtp emailOtp) {
        try {
            otpStatusDetailsDao.setStatus(emailOtp.getId(), OtpStatus.WAITING_FOR_VERIFICATION, "Otp is successfully sent.Now waiting for verification");
        } catch (OtpStatusDetailsNotFoundException e) {
            LOGGER.warn("", e);
        }
    }

    private void setStatusToProcessing(EmailOtp emailOtp) {
        try {
            otpStatusDetailsDao.setStatus(emailOtp.getId(), OtpStatus.PROCESSING, "OtpJobProcessor has picked the OtpCreateRequest");
        } catch (OtpStatusDetailsNotFoundException e) {
            LOGGER.warn("", e);
        }
    }
}
