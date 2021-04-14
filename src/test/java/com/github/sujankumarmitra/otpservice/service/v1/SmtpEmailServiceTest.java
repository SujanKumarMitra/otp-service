package com.github.sujankumarmitra.otpservice.service.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@inheritDoc}
 * <p>
 * Tests for {@link SmtpEmailService}
 *
 * @author skmitra
 * @version 1
 * @see EmailServiceTest
 * @see SmtpEmailService
 */
@SpringBootTest
class SmtpEmailServiceTest extends EmailServiceTest {

    @Autowired
    SmtpEmailService emailService;

    @Override
    @BeforeEach
    void setUp() {
        serviceUnderTest = emailService;
        logger = LoggerFactory.getLogger(getClass());
        super.setUp();
    }

    @Override
    @Test
    void givenValidEmailMessage_whenSendEmail_shouldSendMail() {
        super.givenValidEmailMessage_whenSendEmail_shouldSendMail();
    }

    @Override
    @Test
    void givenInvalidEmailRecipient_whenSendEmail_shouldNotBeAbleToSendMail() {
        super.givenInvalidEmailRecipient_whenSendEmail_shouldNotBeAbleToSendMail();
    }
}
