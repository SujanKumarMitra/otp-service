package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStatusDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStatusDetailsNotFoundException;
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
 * Implements the {@link OtpStatusDetailsDao} on an underlying relational database and JDBC API.
 */
@Repository
public class JdbcOtpStatusDetailsDao implements OtpStatusDetailsDao {

    private static final String INSERT_STATEMENT = "INSERT INTO otp_state_details VALUES(?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM otp_state_details WHERE otp_uuid=?";
    private static final String UPDATE_ALL_STATEMENT = "UPDATE otp_state_details SET current_status=?, reason_phrase=?, total_attempts=? WHERE otp_uuid=?";
    private static final String UPDATE_ATTEMPTS_STATEMENT = "UPDATE otp_state_details SET total_attempts=? WHERE otp_uuid=?";
    private static final String UPDATE_STATE_STATEMENT = "UPDATE otp_state_details SET current_status=?, reason_phrase=? WHERE otp_uuid=?";

    private JdbcTemplate jdbcTemplate;
    private OtpStateDetailsResultSetExtractor resultSetExtractor;

    @Autowired
    public JdbcOtpStatusDetailsDao(JdbcTemplate jdbcTemplate,
                                   OtpStateDetailsResultSetExtractor resultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultSetExtractor = resultSetExtractor;
    }

    @Override
    public void insertStatusDetails(OtpStatusDetails statusDetails) throws OtpStatusDetailsAlreadyExistsException, OtpNotFoundException {
        try {
            jdbcTemplate.update(INSERT_STATEMENT,
                    null,
                    statusDetails.getOtpId(),
                    statusDetails.getCurrentStatus().getState(),
                    statusDetails.getCurrentStatusReasonPhrase(),
                    statusDetails.getTotalVerificationAttemptsMade()
            );
        } catch (DuplicateKeyException e) {
//            otp already exists in db
            throw new OtpStatusDetailsAlreadyExistsException(statusDetails.getOtpId());
        } catch (DataIntegrityViolationException e) {
//            otpId not present in db (foreign key violation)
            throw new OtpNotFoundException(statusDetails.getOtpId());
        }
    }

    @Override
    public Optional<OtpStatusDetails> getStatusDetails(String otpId) {
        OtpStatusDetails otpStatusDetails = jdbcTemplate.query(SELECT_STATEMENT, resultSetExtractor, otpId);
        return Optional.ofNullable(otpStatusDetails);
    }

    @Override
    public void updateStatusDetails(OtpStatusDetails statusDetails) throws OtpStatusDetailsNotFoundException {
        int rowsUpdated = jdbcTemplate.update(UPDATE_ALL_STATEMENT,
                statusDetails.getCurrentStatus().getState(),
                statusDetails.getCurrentStatusReasonPhrase(),
                statusDetails.getTotalVerificationAttemptsMade(),
                statusDetails.getOtpId());
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStatusDetailsNotFoundException(statusDetails.getOtpId());
        }
    }

    @Override
    public void setTotalVerificationAttemptsMade(String otpId, long attemptsMade) throws OtpStatusDetailsNotFoundException {
        if(attemptsMade < 0)
            throw new IllegalArgumentException("attemptsMade can't be negative");
        int rowsUpdated = jdbcTemplate.update(
                UPDATE_ATTEMPTS_STATEMENT,
                attemptsMade,
                otpId);
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStatusDetailsNotFoundException(otpId);
        }
    }

    @Override
    public void setStatus(String otpId, OtpStatus newStatus, String reasonPhrase) throws OtpStatusDetailsNotFoundException {
        int rowsUpdated = jdbcTemplate.update(
                UPDATE_STATE_STATEMENT,
                newStatus.getState(),
                reasonPhrase,
                otpId);
        if(rowsUpdated == 0) {
//           no OtpStateDetails available with otpId
            throw new OtpStatusDetailsNotFoundException(otpId);
        }
    }
}
