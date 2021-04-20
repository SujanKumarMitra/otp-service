package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.InvalidMessageTemplateException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpCreationException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpVerificationException;
import com.github.sujankumarmitra.otpservice.model.v1.*;

/**
 *
 * This interface contains operations related to @{@link Otp}
 * @param <T> type of {@link CreateOtpRequest}
 *
 * @author skmitra
 * @version 1
 */
public interface OtpService<T extends CreateOtpRequest> {

    /**
     * Creates and starts a new otp-transaction
     * @param request request details of new otp transaction
     * @return response
     * @throws OtpCreationException if any error occurs to start a new transaction
     */
    CreateOtpResponse createOtp(T request) throws OtpCreationException, InvalidMessageTemplateException;

    /**
     * This method performs verification of an otp
     * @param request request details
     * @return result of verificatoon
     * @throws OtpVerificationException if error occurs during verification
     * @throws OtpNotFoundException if otpId supplied does not exist
     */
    OtpVerificationResponse verifyOtp(OtpVerificationRequest request) throws OtpVerificationException, OtpNotFoundException;

    /**
     * Returns the current state of an otp-transaction
     * @param otpId the id of the otp
     * @return the state details of the otp
     * @throws OtpNotFoundException if otpId supplied does not exist
     * @see Otp#getId()
     */
    OtpStatusDetails getCurrentOtpStatus(String otpId) throws OtpNotFoundException;
}
