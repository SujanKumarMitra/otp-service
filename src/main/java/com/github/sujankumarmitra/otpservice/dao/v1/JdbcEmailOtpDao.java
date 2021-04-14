package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import com.github.sujankumarmitra.otpservice.util.v1.EmailOtpResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    private JdbcTemplate jdbcTemplate;
    private EmailOtpResultSetExtractor otpResultSetExtractor;

    @Autowired
    public JdbcEmailOtpDao(JdbcTemplate jdbcTemplate, EmailOtpResultSetExtractor otpResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.otpResultSetExtractor = otpResultSetExtractor;
    }

    @Override
    public void save(EmailOtp otp) {
        jdbcTemplate.
                update(OTP_INSERT_STATEMENT,
                        null,
                        otp.getId(),
                        otp.getCode(),
                        otp.getCreatedAt().toEpochMilli(),
                        otp.getDurationBeforeExpiry().toMillis(),
                        otp.getEmailAddress(),
                        otp.getMessageBody());
    }

    @Override
    public Optional<EmailOtp> getOtp(String otpId) {
        EmailOtp emailOtp = jdbcTemplate.query(OTP_SELECT_STATEMENT, otpResultSetExtractor, otpId);
        return Optional.ofNullable(emailOtp);
    }

}
