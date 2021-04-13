package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.model.v1.CreateOtpRequest;

/**
 * This exception is thrown when an otp could not be created
 * Reason must be specified in the {@link #OtpCreationException(String)}
 *
 * @author skmitra
 * @version 1
 * @see com.github.sujankumarmitra.otpservice.service.v1.OtpService#createOtp(CreateOtpRequest)
 */
public class OtpCreationException extends RuntimeException {
    public OtpCreationException(String message) {
        super(message);
    }
}
