package com.github.sujankumarmitra.otpservice.model.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.InvalidOtpStateTransitionException;

/**
 * This interface maintains the state of an otp.
 *
 * @author skmitra
 * @version 1
 */
public interface OtpTransaction {

    /**
     * @return the current state
     */
    OtpState getCurrentState();

    /**
     * @param state the current state
     * @throws InvalidOtpStateTransitionException when invalid state transition is attempted.
     * @apiNote the argument passed must match the state transition rules mentioned in @{@link OtpState}
     */
    void setCurrentState(OtpState state) throws InvalidOtpStateTransitionException;

    /**
     * @return the otp associated with this transaction
     */
    Otp getAssociatedOtp();
}
