package com.github.sujankumarmitra.otpservice.exception.v1;

public class OtpNotFoundException extends RuntimeException {
    public OtpNotFoundException(String otpId) {
        super("Otp with id='" + otpId + "' not found");
    }
}
