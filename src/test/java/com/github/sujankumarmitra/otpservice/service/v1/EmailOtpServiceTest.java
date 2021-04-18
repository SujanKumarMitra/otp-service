package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.CreateEmailOtpRequest;
import com.github.sujankumarmitra.otpservice.model.v1.CreateOtpResponse;
import com.github.sujankumarmitra.otpservice.model.v1.JacksonCompatibleCreateEmailOtpRequest;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests which checks the functionality of {@link EmailOtpService}
 *
 * @author skmitra
 * @version 1
 */
abstract class EmailOtpServiceTest {

    protected EmailOtpService serviceUnderTest;
    protected Logger logger;

    protected static final String VALID_OTP_ID = "c2cb811b-4596-4764-9ae7-ee2605352e60";
    protected static final String INVALID_OTP_ID = "c2cb811b-4596-4764-9ae7-ee2605352e61";
    protected static final String VALID_EMAIL = "mitrakumarsujan@gmail.com";

    @BeforeEach
    void setUp() {
        assertNotNull(serviceUnderTest, () -> "EmailOtpService can't be null");
        assertNotNull(logger, () -> "Logger can't be null");
    }

    @Test
    void givenValidOtpId_whenGetCurrentOtpStatus_shouldFetchGetCurrentOtpStatus() {
        OtpStatusDetails otpStatusDetails = assertDoesNotThrow(() -> serviceUnderTest.getCurrentOtpStatus(VALID_OTP_ID));
        assertNotNull(otpStatusDetails);
        logger.info("{}", otpStatusDetails);
        assertEquals(VALID_OTP_ID, otpStatusDetails.getOtpId());
    }

    @Test
    void givenInvalidOtpId_whenGetCurrentOtpStatus_shouldThrowException() {
        assertThrows(OtpNotFoundException.class,
                () -> serviceUnderTest.getCurrentOtpStatus(INVALID_OTP_ID));
    }

    @Test
    void whenCreateOtp_shouldCreateOtp() {
        CreateEmailOtpRequest request = new JacksonCompatibleCreateEmailOtpRequest(
                VALID_EMAIL,
                "$code$",
                30
        );
        CreateOtpResponse createOtpResponse = assertDoesNotThrow(
                () -> serviceUnderTest.createOtp(request));

        assertNotNull(createOtpResponse);
        logger.info("OtpId {}", createOtpResponse.getOtpId());
    }
}