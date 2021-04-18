package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.model.v1.CreateOtpRequest;
import com.github.sujankumarmitra.otpservice.model.v1.Otp;


/**
 * This interface is used to process an {@link CreateOtpRequest}
 * like sending otp-code to the user
 *
 * @param <T> type of {@link Otp}
 * @author skmitra
 * @version 1
 */
public interface OtpProcessor<T extends Otp> {
    void processOtp(T otp);
}
