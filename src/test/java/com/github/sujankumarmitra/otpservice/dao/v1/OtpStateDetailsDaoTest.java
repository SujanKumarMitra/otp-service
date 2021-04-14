package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.model.v1.BasicOtpStateDetails;
import com.github.sujankumarmitra.otpservice.model.v1.OtpState;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails;
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
abstract class OtpStateDetailsDaoTest {

    protected OtpStateDetailsDao daoUnderTest;
    protected Logger logger;

    protected final String VALID_OTP_ID = "df30f5b1-c876-410d-9b23-9b1d2ff9562d";
    protected final String INVALID_OTP_ID = "df30f5b1-c876-410d-9b23-9b1d2ff9562e";
    protected final String EXISTING_OTP_ID = "1af0da85-67c7-42aa-89ad-c91af4c189cc";

    @BeforeEach
    void setUp() {
        assertNotNull(daoUnderTest, () -> "OtpStateDetailsDao can't be null");
        assertNotNull(logger, () -> "Logger can't be null");
    }

    @Test
    void givenValidOtpStateDetails_whenInserted_shouldInsertOtpStateDetails() {

        OtpStateDetails otpStateDetails = BasicOtpStateDetails.newBuilder()
                .withOtpId(VALID_OTP_ID)
                .withCurrentState(OtpState.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();
        assertDoesNotThrow(() -> daoUnderTest.insertStateDetails(otpStateDetails));
    }

    @Test
    void givenInvalidOtpStateDetails_whenInserted_shouldThrowException() {
        OtpStateDetails otpStateDetails = BasicOtpStateDetails.newBuilder()
                .withOtpId(INVALID_OTP_ID)
                .withCurrentState(OtpState.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();
        assertThrows(OtpNotFoundException.class, () -> daoUnderTest.insertStateDetails(otpStateDetails));
    }

    @Test
    void givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException() {
        OtpStateDetails otpStateDetails = BasicOtpStateDetails.newBuilder()
                .withOtpId(EXISTING_OTP_ID)
                .withCurrentState(OtpState.NEW)
                .withCurrentStateReasonPhrase("JUST CREATED")
                .withTotalVerificationAttemptsMade(0L)
                .build();

        assertThrows(OtpStateDetailsAlreadyExistsException.class, () -> daoUnderTest.insertStateDetails(otpStateDetails));
    }

    @Test
    void givenValidOtpId_whenFetched_shouldFetchOtpStateDetails() {
        Optional<OtpStateDetails> otpStateDetails = daoUnderTest.getStateDetails(EXISTING_OTP_ID);
        assertTrue(otpStateDetails.isPresent());

        String actualOtpId = otpStateDetails.map(OtpStateDetails::getOtpId).get();
        assertEquals(EXISTING_OTP_ID, actualOtpId);
    }
}