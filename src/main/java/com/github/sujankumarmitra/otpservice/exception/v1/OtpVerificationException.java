package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.model.v1.OtpVerificationRequest;

/**
 * This exception is thrown when an otp could not be verified
 * Reason must be specified in the {@link #OtpVerificationException(String)}
 *
 * @author skmitra
 * @version 1
 * @see com.github.sujankumarmitra.otpservice.service.v1.OtpService#verifyOtp(OtpVerificationRequest) 
 */
public class OtpVerificationException extends RuntimeException {
    public OtpVerificationException(String message) {
        super(message);
    }
}
