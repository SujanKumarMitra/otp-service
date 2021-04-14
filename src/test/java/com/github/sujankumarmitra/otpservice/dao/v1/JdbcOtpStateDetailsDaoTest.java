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
 * Tests for {@link JdbcOtpStateDetailsDao}
 *
 * @author skmitra
 * @version 1
 * @see JdbcOtpStateDetailsDao
 */
@SpringBootTest
class JdbcOtpStateDetailsDaoTest extends OtpStateDetailsDaoTest {

    @Autowired
    JdbcOtpStateDetailsDao stateDetailsDao;

    @Override
    @BeforeEach
    void setUp() {
        daoUnderTest = stateDetailsDao;
        logger = LoggerFactory.getLogger(getClass());
        super.setUp();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:/new-schema.sql",
                            "classpath:/insert-valid-otp-state-details.sql"
                    }),
            @Sql(executionPhase = AFTER_TEST_METHOD,
                    scripts = {"classpath:/truncate-all.sql"}
            )
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
            @Sql(executionPhase = BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:/new-schema.sql",
                            "classpath:/insert-existing-otp-state-details.sql"
                    }),
            @Sql(executionPhase = AFTER_TEST_METHOD,
                    scripts = {"classpath:/truncate-all.sql"}
            )
    })
    void givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException() {
        super.givenOtpStateDetailsWithExistingOtpId_whenInserted_shouldThrowException();
    }

    @Override
    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:/new-schema.sql",
                            "classpath:/select-otp-state-details.sql"
                    }),
            @Sql(executionPhase = AFTER_TEST_METHOD,
                    scripts = {"classpath:/truncate-all.sql"}
            )
    })
    void givenValidOtpId_whenFetched_shouldFetchOtpStateDetails() {
        super.givenValidOtpId_whenFetched_shouldFetchOtpStateDetails();
    }
}
