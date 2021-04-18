package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.OtpProperties;
import com.github.sujankumarmitra.otpservice.dao.v1.EmailOtpDao;
import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.BasicOtpStatusDetails;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;
import com.github.sujankumarmitra.otpservice.util.v1.OtpCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

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
    @Autowired
    OtpProperties properties;

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

    private Optional<OtpStatusDetails> getMockOtpStatusDetails(InvocationOnMock invocation) {
        String otpId = invocation.getArgument(0, String.class);
        OtpStatusDetails statusDetails = BasicOtpStatusDetails.newBuilder()
                .withOtpId(otpId)
                .withCurrentStatus(OtpStatus.NEW)
                .withCurrentStatusReasonPhrase("NEW")
                .withTotalVerificationAttemptsMade(0)
                .build();
        return Optional.of(statusDetails);
    }
}
