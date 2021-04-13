package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.model.v1.OtpState;

/**
 * This exception is thrown when an invalid otp-state transition is occurred.
 *
 * @author skmitra
 * @version 1
 * @see com.github.sujankumarmitra.otpservice.model.v1.OtpState
 * @see com.github.sujankumarmitra.otpservice.model.v1.OtpTransaction#setCurrentState(OtpState)
 */
public class InvalidOtpStateTransitionException extends RuntimeException {
}
