package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.Otp;

import java.time.Instant;
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

    /**
     * Returns an {@link Otp} with given otpId.
     * If otp exists then it is wrapped in {@link Optional},
     * else an {@link Optional#empty()} is returned
     *
     * @param otpId the id of the otp
     * @return the otp
     */
    Optional<T> getOtp(String otpId);

    /**
     * Updates the createdAt of an {@link Otp}
     *
     * @param otpId     the id of otp
     * @param createdAt the new createdAt
     */
    void setCreatedAt(String otpId, Instant createdAt) throws OtpNotFoundException;
}