package com.github.sujankumarmitra.otpservice.model.v1;

public interface OtpVerificationResponse {

    boolean isVerified();

    long attemptsRemaining();
}
