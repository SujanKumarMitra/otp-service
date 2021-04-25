package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.util.v1.OtpStateDetailsResultSetExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * {@inheritDoc}
 * <p>
 * Tests for {@link JdbcOtpStatusDetailsDao}
 *
 * @author skmitra
 * @version 1
 * @see JdbcOtpStatusDetailsDao
 */
@DataJdbcTest
class JdbcOtpStatusDetailsDaoTest extends OtpStatusDetailsDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @BeforeEach
    void setUp() {
        daoUnderTest = new JdbcOtpStatusDetailsDao(jdbcTemplate, new OtpStateDetailsResultSetExtractor());
        logger = LoggerFactory.getLogger(getClass());
        super.setUp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/new-schema.sql"),
            @Sql(executionPhase = BEFORE_TEST_METHOD,
                    statements = "INSERT INTO email_otp VALUES (null,'" + VALID_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpStateDetails_whenInserted_shouldInsertOtpStateDetails() {
        super.givenValidOtpStateDetails_whenInserted_shouldInsertOtpStateDetails();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/new-schema.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenInvalidOtpStateDetails_whenInserted_shouldThrowException() {
        super.givenInvalidOtpStateDetails_whenInserted_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException() {
        super.givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpId_whenFetched_shouldFetchOtpStateDetails() {
        super.givenValidOtpId_whenFetched_shouldFetchOtpStateDetails();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpStateDetails_whenUpdated_shouldUpdateOtpStateDetails() {
        super.givenValidOtpStateDetails_whenUpdated_shouldUpdateOtpStateDetails();
    }


    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:/new-schema.sql"}),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"classpath:/truncate-all.sql"})
    })
    void givenInvalidOtpStateDetails_whenUpdated_shouldThrowException() {
        super.givenInvalidOtpStateDetails_whenUpdated_shouldThrowException();
    }


    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpId_whenSetTotalVerificationAttemptsMade_shouldSetTotalVerificationAttemptsMade() {
        super.givenValidOtpId_whenSetTotalVerificationAttemptsMade_shouldSetTotalVerificationAttemptsMade();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpIdAndInvalidAttempts_whenSetTotalVerificationAttemptsMade_shouldThrowException() {
        super.givenValidOtpIdAndInvalidAttempts_whenSetTotalVerificationAttemptsMade_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:/new-schema.sql"}),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"classpath:/truncate-all.sql"})
    })
    void givenInvalidOtpId_whenSetTotalVerificationAttemptsMade_shouldThrowException() {
        super.givenInvalidOtpId_whenSetTotalVerificationAttemptsMade_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/new-schema.sql"),
            @Sql(statements = {
                    "INSERT INTO email_otp VALUES (null,'" + EXISTING_OTP_ID + "','q1X0z!',1618378580139,300000,'mitrakumarsujan@gmail.com','OTP Code: q1X0z!')",
                    "INSERT INTO otp_status_details VALUES(null,'" + EXISTING_OTP_ID + "','NEW','JUST CREATED',0)"
            }),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/truncate-all.sql")
    })
    void givenValidOtpId_whenSetStatus_shouldSetStatus() {
        super.givenValidOtpId_whenSetStatus_shouldSetStatus();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:/new-schema.sql"}),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"classpath:/truncate-all.sql"})
    })
    void givenInvalidOtpId_whenSetStatus_shouldSetStatus() {
        super.givenInvalidOtpId_whenSetStatus_shouldSetStatus();
    }
}
