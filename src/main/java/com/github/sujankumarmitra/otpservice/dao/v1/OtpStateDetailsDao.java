package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.Otp;
import com.github.sujankumarmitra.otpservice.model.v1.OtpState;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails;

import java.util.Optional;

/**
 * This interface is responsible for managing {@link OtpStateDetails} objects.
 *
 * @author skmitra
 * @version 1
 * @see OtpStateDetails
 */
public interface OtpStateDetailsDao {

    /**
     * Inserts a new {@link OtpStateDetails} in the collection
     *
     * @param stateDetails new Otp StateDetails
     * @throws OtpStateDetailsAlreadyExistsException if an attempt is made to insert an {@link OtpStateDetails}
     *                                               with otpId which is already present in the collection
     * @throws OtpNotFoundException                  if an attempt is made to insert an {@link OtpStateDetails} with dangling {@link OtpStateDetails#getOtpId()}
     */
    void insertStateDetails(OtpStateDetails stateDetails) throws OtpStateDetailsAlreadyExistsException, OtpNotFoundException;

    /**
     * Fetches an {@link OtpStateDetails} from the collection.
     * If any {@link OtpStateDetails} exists then it is wrapped in an instance of {@link Optional},
     * otherwise an {@link Optional#empty()} is returned
     *
     * @param otpId the id of the {@link Otp}
     * @return an instance of {@link Optional} with the possibility of containing {@link OtpStateDetails}
     * @apiNote {@link Optional} may be empty, but never null
     */
    Optional<OtpStateDetails> getStateDetails(String otpId);

    /**
     * Updates an existing {@link OtpStateDetails}.
     * The {@link OtpStateDetails#getOtpId()} is used to search the existing details in the collection.
     *
     * @param stateDetails the stateDetails with updated value
     * @throws OtpStateDetailsNotFoundException if no {@link OtpStateDetails} is found with {@link OtpStateDetails#getOtpId()}
     */
    void updateStateDetails(OtpStateDetails stateDetails) throws OtpStateDetailsNotFoundException;

    /**
     * Updates the {@link OtpStateDetails#getTotalVerificationAttemptsMade()} for an Otp
     *
     * @param otpId        the id of the Otp
     * @param attemptsMade total attempts made
     */
    void setTotalVerificationAttemptsMade(String otpId, long attemptsMade) throws OtpStateDetailsNotFoundException;

    /**
     * Updates the {@link OtpStateDetails} current state
     *
     * @param otpId        the id of the otp
     * @param newState     new state
     * @param reasonPhrase reason for state change
     */
    void setState(String otpId, OtpState newState, String reasonPhrase) throws OtpStateDetailsNotFoundException;
}