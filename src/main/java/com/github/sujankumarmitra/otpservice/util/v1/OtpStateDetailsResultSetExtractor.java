package com.github.sujankumarmitra.otpservice.util.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.JdbcOtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.BasicOtpStatusDetails;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@inheritDoc}
 * <p>
 * This class extracts the properties of an {@link OtpStatusDetails} from an {@link ResultSet}
 * Note that, it only extracts the first record from the result set, and ignores the rest
 *
 * @author skmitra
 * @version 1
 * @see JdbcOtpStatusDetailsDao#getStatusDetails(String)
 */
@Component
public class OtpStateDetailsResultSetExtractor implements ResultSetExtractor<OtpStatusDetails> {
    @Override
    public OtpStatusDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (!rs.next()) {
            return null;
        }

        return BasicOtpStatusDetails.newBuilder()
                .withOtpId(rs.getString("otp_uuid"))
                .withCurrentStatus(OtpStatus.valueOf(rs.getString("current_state")))
                .withCurrentStatusReasonPhrase(rs.getString("reason_phrase"))
                .withTotalVerificationAttemptsMade(rs.getLong("total_attempts"))
                .build();
    }
}
