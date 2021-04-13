package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpCreationException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpVerificationException;
import com.github.sujankumarmitra.otpservice.model.v1.*;

public interface OtpService<T extends CreateOtpRequest> {

    CreateOtpResponse createOtp(T request) throws OtpCreationException;

    OtpVerificationResponse verifyOtp(OtpVerificationRequest request) throws OtpVerificationException, OtpNotFoundException;

    OtpStateDetails getCurrentOtpState(String otpId) throws OtpNotFoundException;
}
