package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.BasicOtpStatusDetails;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains test related to functionality of {@link OtpStateDetailsDao}
 *
 * @author skmitra
 * @version 1
 * @see OtpStateDetailsDao
 */
abstract class OtpStatusDetailsDaoTest {

    protected OtpStateDetailsDao daoUnderTest;
    protected Logger logger;

    protected static final String VALID_OTP_ID = "abf9b53a-9b6b-4911-9e44-97afd38cb892";
    protected static final String INVALID_OTP_ID = "df30f5b1-c876-410d-9b23-9b1d2ff9562e";
    protected static final String EXISTING_OTP_ID = "df30f5b1-c876-410d-9b23-9b1d2ff9562d";

    @BeforeEach
    void setUp() {
        assertNotNull(daoUnderTest, () -> "OtpStateDetailsDao can't be null");
        assertNotNull(logger, () -> "Logger can't be null");
    }

    @Test
    void givenValidOtpStateDetails_whenInserted_shouldInsertOtpStateDetails() {

        OtpStatusDetails otpStatusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(VALID_OTP_ID)
                .withCurrentStatus(OtpStatus.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();
        assertDoesNotThrow(() -> daoUnderTest.insertStateDetails(otpStatusDetails));
    }

    @Test
    void givenInvalidOtpStateDetails_whenInserted_shouldThrowException() {
        OtpStatusDetails otpStatusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(INVALID_OTP_ID)
                .withCurrentStatus(OtpStatus.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();
        assertThrows(OtpNotFoundException.class, () -> daoUnderTest.insertStateDetails(otpStatusDetails));
    }

    @Test
    void givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException() {
        OtpStatusDetails otpStatusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(EXISTING_OTP_ID)
                .withCurrentStatus(OtpStatus.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();

        assertThrows(OtpStateDetailsAlreadyExistsException.class, () -> daoUnderTest.insertStateDetails(otpStatusDetails));
    }

    @Test
    void givenValidOtpId_whenFetched_shouldFetchOtpStateDetails() {
        Optional<OtpStatusDetails> otpStateDetails = daoUnderTest.getStateDetails(EXISTING_OTP_ID);
        assertTrue(otpStateDetails.isPresent());

        String actualOtpId = otpStateDetails.map(OtpStatusDetails::getOtpId).get();
        assertEquals(EXISTING_OTP_ID, actualOtpId);
    }

    @Test
    void givenValidOtpStateDetails_whenUpdated_shouldUpdateOtpStateDetails() {
        OtpStatusDetails otpStatusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(EXISTING_OTP_ID)
                .withCurrentStatus(OtpStatus.PROCESSING)
                .withCurrentStateReasonPhrase("JUST UPDATED")
                .withTotalVerificationAttemptsMade(1L)
                .build();
        assertDoesNotThrow(() -> daoUnderTest.updateStateDetails(otpStatusDetails));
    }

    @Test
    void givenInvalidOtpStateDetails_whenUpdated_shouldThrowException() {
        OtpStatusDetails otpStatusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(INVALID_OTP_ID)
                .withCurrentStatus(OtpStatus.PROCESSING)
                .withCurrentStateReasonPhrase("JUST UPDATED")
                .withTotalVerificationAttemptsMade(1L)
                .build();
        assertThrows(OtpStateDetailsNotFoundException.class,
                () -> daoUnderTest.updateStateDetails(otpStatusDetails));
    }
}