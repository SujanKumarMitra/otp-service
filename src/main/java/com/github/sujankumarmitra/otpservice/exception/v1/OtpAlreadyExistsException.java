package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.model.v1.Otp;

/**
 * This exception is thrown when an attempt is made to save
 * an {@link Otp} with an {@link Otp#getId()} which already exists in the collection.
 *
 * @author skmitra
 * @version 1
 */
public class OtpAlreadyExistsException extends RuntimeException {

    public OtpAlreadyExistsException(String otpId) {
        super("Otp with id='" + otpId + "' already exists!");
    }
}
