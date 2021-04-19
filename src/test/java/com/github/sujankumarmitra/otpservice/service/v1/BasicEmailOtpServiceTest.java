package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.*;
import com.github.sujankumarmitra.otpservice.util.v1.OtpCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static java.time.Duration.ofSeconds;
import static java.time.Instant.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@inheritDoc}
 * <p>
 * Tests for {@link BasicEmailOtpService}
 */
@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class BasicEmailOtpServiceTest extends EmailOtpServiceTest {

    @Autowired
    BasicEmailOtpService service;

    @MockBean
    OtpCodeGenerator codeGenerator;
    @MockBean
    EmailOtpDao otpDao;
    @MockBean
    OtpStatusDetailsDao otpStatusDetailsDao;
    @MockBean
    ExecutorService executorService;

    @Override
    @BeforeEach
    void setUp() {
        service.setExecutorService(executorService);
        serviceUnderTest = service;
        logger = LoggerFactory.getLogger(getClass());
        super.setUp();
    }

    @Override
    @Test
    void givenValidOtpId_whenGetCurrentOtpStatus_shouldFetchGetCurrentOtpStatus() {
        doAnswer(this::getMockOtpStatusDetails)
                .when(otpStatusDetailsDao)
                .getStatusDetails(any());
        super.givenValidOtpId_whenGetCurrentOtpStatus_shouldFetchGetCurrentOtpStatus();
    }

    @Override
    @Test
    void givenInvalidOtpId_whenGetCurrentOtpStatus_shouldThrowException() {
        doAnswer(invocation -> Optional.empty())
                .when(otpStatusDetailsDao)
                .getStatusDetails(INVALID_OTP_ID);

        super.givenInvalidOtpId_whenGetCurrentOtpStatus_shouldThrowException();
    }

    @Override
    @Test
    void whenCreateOtp_shouldCreateOtp() {

        doNothing()
                .when(otpDao)
                .save(any());

        doNothing()
                .when(otpStatusDetailsDao)
                .insertStatusDetails(any());

        doReturn(null)
                .when(executorService)
                .submit((Runnable) any());

        doReturn("q21!Z9")
                .when(codeGenerator)
                .generateNewOtpCode();

        super.whenCreateOtp_shouldCreateOtp();
    }

    @Override
    @Test
    void givenValidOtpVerificationCode_whenVerifyOtp_shouldVerify() {
        doAnswer(this::getMockOtpStatusDetails)
                .when(otpStatusDetailsDao)
                .getStatusDetails(VALID_OTP_ID);
        doAnswer(this::getMockEmailOtp)
                .when(otpDao)
                .getOtp(VALID_OTP_ID);

        doNothing()
                .when(otpStatusDetailsDao)
                .setStatus(any(),any(),any());

        super.givenValidOtpVerificationCode_whenVerifyOtp_shouldVerify();
    }

    @Override
    @Test
    void givenInvalidOtpId_whenVerifyOtp_shouldThrowException() {
        doReturn(Optional.empty())
                .when(otpDao)
                .getOtp(INVALID_OTP_ID);
        doReturn(Optional.empty())
                .when(otpStatusDetailsDao)
                .getStatusDetails(INVALID_OTP_ID);

        super.givenInvalidOtpId_whenVerifyOtp_shouldThrowException();
    }

    @Override
    @Test
    void givenExpiredOtp_whenVerifyOtp_shouldNotVerifyOtp() {
        Mockito.doAnswer(this::getExpiredEmailOtp)
                .when(otpDao)
                .getOtp(EXPIRED_OTP_ID);

        Mockito.doAnswer(this::getMockOtpStatusDetails)
                .when(otpStatusDetailsDao)
                .getStatusDetails(EXPIRED_OTP_ID);
        super.givenExpiredOtp_whenVerifyOtp_shouldNotVerifyOtp();
    }

    @Override
    @Test
    void givenInvalidOtp_whenVerifyOtp_shouldNotVerifyOtp() {
        Mockito.doAnswer(this::getMockEmailOtp)
                .when(otpDao)
                .getOtp(INVALID_STATUS_OTP_ID);

        Mockito.doAnswer(this::getInvalidStatusOtpStateDetails)
                .when(otpStatusDetailsDao)
                .getStatusDetails(INVALID_STATUS_OTP_ID);
        super.givenInvalidOtp_whenVerifyOtp_shouldNotVerifyOtp();
    }

    @Override
    @Test
    void givenValidOtpIdButIncorrectOtpCode_whenVerifyOtp_shouldNotVerifyOtp() {
        doAnswer(this::getMockOtpStatusDetails)
                .when(otpStatusDetailsDao)
                .getStatusDetails(VALID_OTP_ID);
        doAnswer(this::getMockEmailOtp)
                .when(otpDao)
                .getOtp(VALID_OTP_ID);

        doNothing()
                .when(otpStatusDetailsDao)
                .setStatus(any(),any(),any());
        super.givenValidOtpIdButIncorrectOtpCode_whenVerifyOtp_shouldNotVerifyOtp();
    }

    private Optional<OtpStatusDetails> getInvalidStatusOtpStateDetails(InvocationOnMock invocationOnMock) {
        String otpId = invocationOnMock.getArgument(0, String.class);
        OtpStatusDetails invalid = BasicOtpStatusDetails.newBuilder()
                .withOtpId(otpId)
                .withCurrentStatus(OtpStatus.INVALID)
                .withCurrentStatusReasonPhrase("invalid")
                .withTotalVerificationAttemptsMade(3)
                .build();
        return Optional.of(invalid);
    }

    private Optional<EmailOtp> getExpiredEmailOtp(InvocationOnMock invocationOnMock) {
        String otpId = invocationOnMock.getArgument(0, String.class);
        EmailOtp emailOtp = BasicEmailOtp.newBuilder()
                .withId(otpId)
                .withCode(VALID_OTP_CODE)
                .withEmailAddress(VALID_EMAIL)
                .withMessageBody("$code$")
                .withCreatedAt(now().minus(ofSeconds(31)))
                .withDurationBeforeExpiry(ofSeconds(30))
                .build();
        return Optional.of(emailOtp);
    }

    private Optional<OtpStatusDetails> getMockOtpStatusDetails(InvocationOnMock invocation) {
        String otpId = invocation.getArgument(0, String.class);
        OtpStatusDetails statusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(otpId)
                .withCurrentStatus(OtpStatus.WAITING_FOR_VERIFICATION)
                .withCurrentStatusReasonPhrase("WAITING_FOR_VERIFICATION")
                .withTotalVerificationAttemptsMade(0L)
                .build();
        return Optional.of(statusDetails);
    }

    private Optional<EmailOtp> getMockEmailOtp(InvocationOnMock invocation) {
        String otpId = invocation.getArgument(0, String.class);
        EmailOtp emailOtp = BasicEmailOtp.newBuilder()
                .withId(otpId)
                .withCode(VALID_OTP_CODE)
                .withCreatedAtNow()
                .withDurationBeforeExpiry(ofSeconds(30))
                .withMessageBody("$code$")
                .withEmailAddress(VALID_EMAIL)
                .build();
        return Optional.of(emailOtp);
    }
}
