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
     * @return state enum
     */
    OtpState getState();

    /**
     * @return the reason for which the otp-transaction is currently at
     */
    String getCurrentStateReasonPhrase();
}
