package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.BasicEmailOtp;
import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains test related to functionality of {@link EmailOtpDao}
 *
 * @author skmitra
 * @version 1
 * @see EmailOtpDao
 */
abstract class EmailOtpDaoTest {

    protected EmailOtpDao daoUnderTest;
    protected Logger logger;

    protected static final String VALID_OTP_ID = "abf9b53a-9b6b-4911-9e44-97afd38cb892";
    protected static final String INVALID_OTP_ID = "abf9b53a-9b6b-4911-9e44-97afd38cb893";
    protected static final String EXISTING_OTP_ID = "86746728-4796-4fc6-a976-3eb34af6a58d";

    @BeforeEach
    void setUp() {
        assertNotNull(daoUnderTest, () -> "EmailOtpDao can't be null");
        assertNotNull(logger, () -> "Logger can't be null");
    }

    @Test
    void givenValidOtp_whenSaved_shouldSaveOtp() {
        EmailOtp emailOtp = BasicEmailOtp.newBuilder()
                .withId(randomUUID().toString())
                .withCode("q1X0z!")
                .withCreatedAtNow()
                .withDurationBeforeExpiry(Duration.ofMinutes(5))
                .withEmailAddress("mitrakumarsujan@gmail.com")
                .withMessageBody("OTP Code: abcd")
                .build();

        assertDoesNotThrow(() -> daoUnderTest.save(emailOtp));
    }

    @Test
    void givenOtpWithExistingId_whenSaved_shouldThrowException() {
        EmailOtp emailOtp = BasicEmailOtp.newBuilder()
                .withId(EXISTING_OTP_ID)
                .withCode("q1X0z!")
                .withCreatedAtNow()
                .withDurationBeforeExpiry(Duration.ofMinutes(5))
                .withEmailAddress("mitrakumarsujan@gmail.com")
                .withMessageBody("OTP Code: abcd")
                .build();

        assertThrows(OtpAlreadyExistsException.class, ()-> daoUnderTest.save(emailOtp));
    }

    @Test
    void givenValidOtpId_whenGetOtp_shouldFetchOtp() {
        Optional<EmailOtp> otp = daoUnderTest.getOtp(VALID_OTP_ID);
        assertTrue(otp.isPresent());

        String actualOtpId = otp.map(EmailOtp::getId).get();
        assertEquals(VALID_OTP_ID, actualOtpId);
    }

    @Test
    void givenInvalidOtpId_whenGetOtp_shouldFetchOtp() {
        Optional<EmailOtp> otp = daoUnderTest.getOtp(INVALID_OTP_ID);
        assertTrue(otp.isEmpty());
    }

    @Test
    void givenValidOtpId_whenSetCreatedAt_shouldSetCreatedAt() {
        assertDoesNotThrow(()-> daoUnderTest.setCreatedAt(EXISTING_OTP_ID, Instant.now()));
    }

    @Test
    void givenInvalidOtpId_whenSetCreatedAt_shouldThrowException() {
        assertThrows(OtpNotFoundException.class,
                ()-> daoUnderTest.setCreatedAt(INVALID_OTP_ID, Instant.now()));
    }
}