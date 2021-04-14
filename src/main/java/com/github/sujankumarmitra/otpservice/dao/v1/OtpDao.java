package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.model.v1.Otp;

import java.util.Optional;

/**
 * This interface is responsible for storage and retrieval of {@link Otp}s.
 *
 * @param <T> type of {@link Otp}
 * @author skmitra
 * @version 1
 * @see Otp
 */
public interface OtpDao<T extends Otp> {

    /**
     * Saves a new Otp in the collection
     *
     * @param otp otp otp to save
     */
    void save(T otp) throws OtpAlreadyExistsException;

    Optional<T> getOtp(String otpId);
}