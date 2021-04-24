package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.ValidatableEmailProperties;
import com.github.sujankumarmitra.otpservice.configuration.v1.ValidatableOtpProperties;
import com.github.sujankumarmitra.otpservice.configuration.v1.EmailProperties;
import com.github.sujankumarmitra.otpservice.configuration.v1.OtpProperties;
import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class BasicEmailOtpProcessorTest extends EmailOtpProcessorTest {

    @Autowired
    BasicEmailOtpProcessor processor;
    @Autowired
    Config config;


    @Configuration
    @EnableConfigurationProperties({ValidatableOtpProperties.class, ValidatableEmailProperties.class})
    static class Config {

        @MockBean
        OtpStatusDetailsDao otpStatusDetailsDao;
        @MockBean
        EmailOtpDao emailOtpDao;
        @MockBean
        EmailService emailService;

        @Autowired
        OtpProperties otpProperties;
        @Autowired
        EmailProperties emailProperties;

        @Bean
        public BasicEmailOtpProcessor processor() {
            return new BasicEmailOtpProcessor(
                    otpStatusDetailsDao,
                    emailOtpDao,
                    emailService,
                    otpProperties,
                    emailProperties);
        }
    }

    @Override
    @BeforeEach
    void setUp() {
        processorUnderTest = processor;
        super.setUp();
    }

    @Override
    @Test
    void givenValidOtp_whenProcess_shouldProcessOtp() {
        super.givenValidOtp_whenProcess_shouldProcessOtp();
    }
}
