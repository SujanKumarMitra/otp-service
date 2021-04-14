package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails;
import com.github.sujankumarmitra.otpservice.util.v1.OtpStateDetailsResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@inheritDoc}
 * <p>
 * Implements the {@link OtpStateDetailsDao} on an underlying relational database and JDBC API.
 */
@Repository
public class JdbcOtpStateDetailsDao implements OtpStateDetailsDao {

    private static final String INSERT_STATEMENT = "INSERT INTO otp_state_details VALUES(?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM otp_state_details WHERE otp_uuid=?";

    private JdbcTemplate jdbcTemplate;
    private OtpStateDetailsResultSetExtractor resultSetExtractor;

    @Autowired
    public JdbcOtpStateDetailsDao(JdbcTemplate jdbcTemplate,
                                  OtpStateDetailsResultSetExtractor resultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultSetExtractor = resultSetExtractor;
    }

    @Override
    public void insertStateDetails(OtpStateDetails stateDetails) throws OtpStateDetailsAlreadyExistsException, OtpNotFoundException {
        try {
            jdbcTemplate.update(INSERT_STATEMENT,
                    null,
                    stateDetails.getOtpId(),
                    stateDetails.getCurrentState().getState(),
                    stateDetails.getCurrentStateReasonPhrase(),
                    stateDetails.getTotalVerificationAttemptsMade()
            );
        } catch (DuplicateKeyException e) {
//            otp already exists in db
            throw new OtpStateDetailsAlreadyExistsException(stateDetails.getOtpId());
        } catch (DataIntegrityViolationException e) {
//            otpId not present in db (foreign key violation)
            throw new OtpNotFoundException(stateDetails.getOtpId());
        }
    }

    @Override
    public Optional<OtpStateDetails> getStateDetails(String otpId) {
        OtpStateDetails otpStateDetails = jdbcTemplate.query(SELECT_STATEMENT, resultSetExtractor, otpId);
        return Optional.ofNullable(otpStateDetails);
    }

    @Override
    public void updateStateDetails(OtpStateDetails stateDetails) throws OtpStateDetailsNotFoundException {
        throw new RuntimeException("not implemented yet!");
    }
}
