package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.model.v1.BasicEmailOtp;
import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailOtpProcessorTest {

    protected EmailOtpProcessor processorUnderTest;

    protected static final String VALID_OTP_ID = "c9db25e0-2144-40c8-ae19-65412beb8472";
    protected static final String VALID_EMAIL = "mitrakumarsujan@gmail.com";
    protected static final String VALID_OTP_CODE = "!w12Zs";

    @BeforeEach
    void setUp() {
        assertNotNull(processorUnderTest);
    }

    @Test
    void givenValidOtp_whenProcess_shouldProcessOtp() {
        EmailOtp otp = BasicEmailOtp
                .newBuilder()
                .withId(VALID_OTP_ID)
                .withCode(VALID_OTP_CODE)
                .withCreatedAtNow()
                .withDurationBeforeExpiry(Duration.ofSeconds(30))
                .withEmailAddress(VALID_EMAIL)
                .withMessageBody("$code$")
                .build();

        assertDoesNotThrow(()-> processorUnderTest.processOtp(otp));
    }
}