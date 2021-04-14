package com.github.sujankumarmitra.otpservice.util.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.JdbcOtpStateDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.BasicOtpStateDetails;
import com.github.sujankumarmitra.otpservice.model.v1.OtpState;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@inheritDoc}
 * <p>
 * This class extracts the properties of an {@link OtpStateDetails} from an {@link ResultSet}
 * Note that, it only extracts the first record from the result set, and ignores the rest
 *
 * @author skmitra
 * @version 1
 * @see JdbcOtpStateDetailsDao#getStateDetails(String)
 */
@Component
public class OtpStateDetailsResultSetExtractor implements ResultSetExtractor<OtpStateDetails> {
    @Override
    public OtpStateDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (!rs.next()) {
            return null;
        }

        return BasicOtpStateDetails.newBuilder()
                .withOtpId(rs.getString("otp_uuid"))
                .withCurrentState(OtpState.valueOf(rs.getString("current_state")))
                .withCurrentStateReasonPhrase(rs.getString("reason_phrase"))
                .withTotalVerificationAttemptsMade(rs.getLong("total_attempts"))
                .build();
    }
}
