package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import com.github.sujankumarmitra.otpservice.util.v1.EmailOtpResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * {@inheritDoc}
 * <p>
 * Implements the {@link EmailOtpDao} on an underlying relational database and JDBC API.
 */
@Repository
public class JdbcEmailOtpDao implements EmailOtpDao {

    private static final String OTP_INSERT_STATEMENT = "INSERT INTO email_otp VALUES (?,?,?,?,?,?,?)";
    private static final String OTP_SELECT_STATEMENT = "SELECT * from email_otp WHERE uuid=?";
    private static final String OTP_UPDATE_CREATED_AT_STATEMENT = "UPDATE email_otp SET created_at=? WHERE uuid=?";

    private JdbcTemplate jdbcTemplate;
    private EmailOtpResultSetExtractor otpResultSetExtractor;

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcEmailOtpDao.class);

    @Autowired
    public JdbcEmailOtpDao(JdbcTemplate jdbcTemplate, EmailOtpResultSetExtractor otpResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.otpResultSetExtractor = otpResultSetExtractor;
    }

    @Override
    public void save(EmailOtp otp) throws OtpAlreadyExistsException {
        LOGGER.info("Saving Otp with id={}", otp.getId());
        try {
            jdbcTemplate.
                    update(OTP_INSERT_STATEMENT,
                            null, // auto-generated id
                            otp.getId(),
                            otp.getCode(),
                            otp.getCreatedAt().toEpochMilli(),
                            otp.getDurationBeforeExpiry().toMillis(),
                            otp.getEmailAddress(),
                            otp.getMessageBody());
        } catch (DuplicateKeyException e) {
            LOGGER.warn("",e);
            throw new OtpAlreadyExistsException(otp.getId());
        }
    }

    @Override
    public Optional<EmailOtp> getOtp(String otpId) {
        LOGGER.info("Fetching Otp of id={}", otpId);
        EmailOtp emailOtp = jdbcTemplate.query(OTP_SELECT_STATEMENT, otpResultSetExtractor, otpId);
        if(emailOtp == null)
            LOGGER.info("No otp found with id={}",otpId);
        return Optional.ofNullable(emailOtp);
    }

    @Override
    public void setCreatedAt(String otpId, Instant createdAt) throws OtpNotFoundException {
        LOGGER.info("Updating CreatedAt of Otp with id={}", otpId);
        int rowsUpdated = jdbcTemplate.update(
                OTP_UPDATE_CREATED_AT_STATEMENT, createdAt.toEpochMilli(),otpId);
        if (rowsUpdated == 0) {
            LOGGER.info("No otp found with id={}",otpId);
            throw new OtpNotFoundException(otpId);
        }
    }
}
