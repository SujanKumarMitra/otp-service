package com.github.sujankumarmitra.otpservice.util.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.JdbcEmailOtpDao;
import com.github.sujankumarmitra.otpservice.model.v1.BasicEmailOtp;
import com.github.sujankumarmitra.otpservice.model.v1.EmailOtp;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

/**
 * {@inheritDoc}
 * <p>
 * This class extracts the properties of an {@link EmailOtp} from an {@link ResultSet}
 * Note that, it only extracts the first record from the result set, and ignores the rest
 *
 * @author skmitra
 * @version 1
 * @see JdbcEmailOtpDao#getOtp(String)
 */
@Component
public class EmailOtpResultSetExtractor implements ResultSetExtractor<EmailOtp> {

    /**
     * {@inheritDoc}
     * Returns null if {@link ResultSet} is empty.
     * Else, constructs an EmailOtp from the first row of {@link ResultSet}
     */
    @Override
    public EmailOtp extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (!rs.next()) {
            //empty ResultSet
            return null;
        }
        return BasicEmailOtp.newBuilder()
                .withId(rs.getString("uuid"))
                .withCode(rs.getString("code"))
                .withCreatedAt(Instant.ofEpochMilli(rs.getLong("created_at")))
                .withDurationBeforeExpiry(Duration.ofMillis(rs.getLong("duration_before_expiry")))
                .withEmailAddress(rs.getString("email_address"))
                .withMessageBody(rs.getString("message_body"))
                .build();
    }
}
