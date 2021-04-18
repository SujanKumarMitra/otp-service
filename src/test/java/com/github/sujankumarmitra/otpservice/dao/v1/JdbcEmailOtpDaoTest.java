package com.github.sujankumarmitra.otpservice.dao.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * {@inheritDoc}
 * <p>
 * Tests for {@link JdbcEmailOtpDao}
 *
 * @author skmitra
 * @version 1
 * @see JdbcEmailOtpDao
 */
@SpringBootTest
class JdbcEmailOtpDaoTest extends EmailOtpDaoTest {

    @Autowired
    JdbcEmailOtpDao emailOtpDao;

    @Override
    @BeforeEach
    void setUp() {
        daoUnderTest = emailOtpDao;
        logger = LoggerFactory.getLogger(getClass());
        super.setUp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/new-schema.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtp_whenSaved_shouldSaveOtp() {
        super.givenValidOtp_whenSaved_shouldSaveOtp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!' )"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenOtpWithExistingId_whenSaved_shouldThrowException() {
        super.givenOtpWithExistingId_whenSaved_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts ="classpath:/new-schema.sql"),
            @Sql(statements = "INSERT INTO email_otp VALUES (null,'" + VALID_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!' )"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpId_whenGetOtp_shouldFetchOtp() {
        super.givenValidOtpId_whenGetOtp_shouldFetchOtp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/new-schema.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenInvalidOtpId_whenGetOtp_shouldFetchOtp() {
        super.givenInvalidOtpId_whenGetOtp_shouldFetchOtp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts ="classpath:/new-schema.sql"),
            @Sql(statements = "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!' )"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpId_whenSetCreatedAt_shouldSetCreatedAt() {
        super.givenValidOtpId_whenSetCreatedAt_shouldSetCreatedAt();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/new-schema.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenInvalidOtpId_whenSetCreatedAt_shouldThrowException() {
        super.givenInvalidOtpId_whenSetCreatedAt_shouldThrowException();
    }
}
