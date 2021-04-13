package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.model.v1.Otp;

import java.util.Optional;

public interface OtpDao<T extends Otp> {

    void save(T otp);

    Optional<T> getOtp(String optId);
}