package com.github.sujankumarmitra.otpservice.exception.v1;

/**
 * This exception is thrown when an invalid otp-state transition is occurred.
 *
 * @author skmitra
 * @version 1
 * @see com.github.sujankumarmitra.otpservice.model.v1.OtpState
 * @see com.github.sujankumarmitra.otpservice.model.v1.OtpTransaction#setCurrentState(com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails)
 */
public class InvalidOtpStateTransitionException extends RuntimeException {
}