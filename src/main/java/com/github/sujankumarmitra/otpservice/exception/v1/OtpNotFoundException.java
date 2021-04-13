package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.model.v1.Otp;

/**
 * This exception is thrown when any operation(except create) is attempted on an non-existing {@link Otp}
 *
 * @author skmitra
 * @version 1
 */
public class OtpNotFoundException extends RuntimeException {
    /**
     * @param otpId the id of the otp
     */
    public OtpNotFoundException(String otpId) {
        super("Otp with id='" + otpId + "' not found");
    }
}
