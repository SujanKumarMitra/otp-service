package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;
import com.github.sujankumarmitra.otpservice.model.v1.BasicEmailMessage;
import com.github.sujankumarmitra.otpservice.model.v1.EmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests of functionality of {@link EmailService}
 *
 * @author skmitra
 * @version 1
 * @see EmailService
 */
abstract class EmailServiceTest {

    public static final String VALID_EMAIL_ADDRESS = "mitrakumarsujan@gmail.com";
    public static final String INVALID_EMAIL_ADDRESS = "xyz@.cdf";

    protected EmailService serviceUnderTest;
    protected Logger logger;

    @BeforeEach
    void setUp() {
        assertNotNull(serviceUnderTest, () -> "EmailService can't be null");
        assertNotNull(logger, () -> "Logger can't be null");
    }

    @Test
    void givenValidEmailMessage_whenSendEmail_shouldSendMail() {
        EmailMessage message = BasicEmailMessage.newBuilder()
                .withRecipient(VALID_EMAIL_ADDRESS)
                .withSubject("Test Subject")
                .withBody("Test Body")
                .build();

        assertDoesNotThrow(() -> serviceUnderTest.sendEmail(message));
    }

    @Test
    void givenInvalidEmailRecipient_whenSendEmail_shouldNotBeAbleToSendMail() {
        EmailMessage message = BasicEmailMessage.newBuilder()
                .withRecipient(INVALID_EMAIL_ADDRESS)
                .withSubject("Test Subject")
                .withBody("Test Body")
                .build();

        assertThrows(EmailMessagingException.class, () -> serviceUnderTest.sendEmail(message));
    }
}