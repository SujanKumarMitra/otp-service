package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * Represents current state details of an otp transaction
 *
 * @author skmitra
 * @version 1
 * @see OtpState
 */
public interface OtpStateDetails {

    /**
     * @return the otpId associated with this state
     * @see Otp#getId()
     */
    String getOtpId();

    /**
     * @return state enum
     */
    OtpState getCurrentState();

    /**
     * @return the reason for which the {@link #getCurrentState()} is currently at.
     */
    String getCurrentStateReasonPhrase();
}
