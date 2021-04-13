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
     * @return the otp associated with this transaction
     */
    Otp getAssociatedOtp();

    /**
     * @return the current state details
     */
    OtpStateDetails getCurrentState();

    /**
     * @param stateDetails the current state details
     * @throws InvalidOtpStateTransitionException when invalid state transition is attempted.
     * @apiNote the {@link OtpStateDetails#getState()} passed must match the state transition rules mentioned in @{@link OtpState}
     */
    void setCurrentState(OtpStateDetails stateDetails) throws InvalidOtpStateTransitionException;
}