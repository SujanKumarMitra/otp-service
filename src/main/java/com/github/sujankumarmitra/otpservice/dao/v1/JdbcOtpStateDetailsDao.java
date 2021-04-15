package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;
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
    private static final String UPDATE_ALL_STATEMENT = "UPDATE otp_state_details SET current_state=?, reason_phrase=?, total_attempts=? WHERE otp_uuid=?";
    private static final String UPDATE_ATTEMPTS_STATEMENT = "UPDATE otp_state_details SET total_attempts=? WHERE otp_uuid=?";
    private static final String UPDATE_STATE_STATEMENT = "UPDATE otp_state_details SET current_state=?, reason_phrase=? WHERE otp_uuid=?";

    private JdbcTemplate jdbcTemplate;
    private OtpStateDetailsResultSetExtractor resultSetExtractor;

    @Autowired
    public JdbcOtpStateDetailsDao(JdbcTemplate jdbcTemplate,
                                  OtpStateDetailsResultSetExtractor resultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultSetExtractor = resultSetExtractor;
    }

    @Override
    public void insertStateDetails(OtpStatusDetails stateDetails) throws OtpStateDetailsAlreadyExistsException, OtpNotFoundException {
        try {
            jdbcTemplate.update(INSERT_STATEMENT,
                    null,
                    stateDetails.getOtpId(),
                    stateDetails.getCurrentStatus().getState(),
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
    public Optional<OtpStatusDetails> getStateDetails(String otpId) {
        OtpStatusDetails otpStatusDetails = jdbcTemplate.query(SELECT_STATEMENT, resultSetExtractor, otpId);
        return Optional.ofNullable(otpStatusDetails);
    }

    @Override
    public void updateStateDetails(OtpStatusDetails stateDetails) throws OtpStateDetailsNotFoundException {
        int rowsUpdated = jdbcTemplate.update(UPDATE_ALL_STATEMENT,
                stateDetails.getCurrentStatus().getState(),
                stateDetails.getCurrentStateReasonPhrase(),
                stateDetails.getTotalVerificationAttemptsMade(),
                stateDetails.getOtpId());
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStateDetailsNotFoundException(stateDetails.getOtpId());
        }
    }

    @Override
    public void setTotalVerificationAttemptsMade(String otpId, long attemptsMade) throws OtpStateDetailsNotFoundException{
        int rowsUpdated = jdbcTemplate.update(
                UPDATE_ATTEMPTS_STATEMENT,
                attemptsMade,
                otpId);
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStateDetailsNotFoundException(otpId);
        }
    }

    @Override
    public void setState(String otpId, OtpStatus newState, String reasonPhrase) throws OtpStateDetailsNotFoundException{
        int rowsUpdated = jdbcTemplate.update(
                UPDATE_STATE_STATEMENT,
                newState.getState(),
                reasonPhrase,
                otpId);
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStateDetailsNotFoundException(otpId);
        }
    }
}
