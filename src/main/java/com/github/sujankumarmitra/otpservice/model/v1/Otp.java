package com.github.sujankumarmitra.otpservice.model.v1;

import java.time.Duration;
import java.time.Instant;

/**
 * This interface represent the otp that was created due to {@link CreateOtpRequest}
 *
 * @author skmitra
 * @version 1
 * @see CreateOtpRequest
 */
public interface Otp {

    /**
     * Represents an unique id of the otp
     *
     * @return the id
     * @apiNote This id is not necessarily represent the actual otp-code which is to be sent to the client
     */
    String getId();

    /**
     * Represents the actual code which is sent to the client and which will be validated.
     *
     * @return the code
     * @apiNote the code can be numeric, alphabets, alphanumeric.
     */
    String getCode();

    /**
     * Represents the instant at which this otp is created.
     *
     * @return the instant at which otp was created.
     */
    Instant getCreatedAt();

    /**
     * Represents the duration after {@link #getCreatedAt()},
     * following which, the otp will become {@link OtpState#EXPIRED}.
     *
     * @return the duration of validity after which, this otp will become invalid
     */
    Duration getDurationBeforeExpiry();
}
