package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static com.github.sujankumarmitra.otpservice.model.v1.OtpStatus.*;
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
    protected static final String INVALID_STATUS_OTP_ID = "ce98d9eb-6759-4cf7-bb21-d2255392cb4f";
    protected static final String EXPIRED_OTP_ID = "ce98d9eb-6759-4cf7-bb21-d2255392cb4d";
    protected static final String VALID_EMAIL = "mitrakumarsujan@gmail.com";

    protected static final String VALID_OTP_CODE = "aE2!zE";
    protected static final String INVALID_OTP_CODE = "qwxzaw";

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

    @Test
    void givenValidOtpVerificationCode_whenVerifyOtp_shouldVerify() {
        OtpVerificationRequest request = new JacksonCompatibleOtpVerificationRequest(VALID_OTP_ID, VALID_OTP_CODE);
        OtpVerificationResponse response = assertDoesNotThrow(() -> serviceUnderTest.verifyOtp(request));

        assertNotNull(response);
        assertTrue(response.isVerified());
        assertEquals(VERIFIED, response.getStatus());

        logger.info("{}", response);
    }

    @Test
    void givenInvalidOtpId_whenVerifyOtp_shouldThrowException() {
        OtpVerificationRequest request = new JacksonCompatibleOtpVerificationRequest(INVALID_OTP_ID, VALID_OTP_CODE);
        assertThrows(OtpNotFoundException.class, () -> serviceUnderTest.verifyOtp(request));
    }

    @Test
    void givenExpiredOtp_whenVerifyOtp_shouldNotVerifyOtp() {
        OtpVerificationRequest request = new JacksonCompatibleOtpVerificationRequest(EXPIRED_OTP_ID, VALID_OTP_CODE);
        OtpVerificationResponse response = assertDoesNotThrow(() -> serviceUnderTest.verifyOtp(request));

        assertFalse(response.isVerified());
        assertEquals(EXPIRED, response.getStatus());

        logger.info("{}", response);
    }

    @Test
    void givenInvalidOtp_whenVerifyOtp_shouldNotVerifyOtp() {
        OtpVerificationRequest request = new JacksonCompatibleOtpVerificationRequest(INVALID_STATUS_OTP_ID, VALID_OTP_CODE);
        OtpVerificationResponse response = assertDoesNotThrow(() -> serviceUnderTest.verifyOtp(request));

        assertFalse(response.isVerified());
        assertEquals(INVALID, response.getStatus());

        logger.info("{}", response);
    }

    @Test
    void givenValidOtpIdButIncorrectOtpCode_whenVerifyOtp_shouldNotVerifyOtp() {
        OtpVerificationRequest request = new JacksonCompatibleOtpVerificationRequest(VALID_OTP_ID, INVALID_OTP_CODE);
        OtpVerificationResponse response = assertDoesNotThrow(() -> serviceUnderTest.verifyOtp(request));

        assertFalse(response.isVerified());
        assertEquals(WAITING_FOR_VERIFICATION, response.getStatus());

        logger.info("{}", response);
    }

}