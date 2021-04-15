package com.github.sujankumarmitra.otpservice.dao.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsAlreadyExistsException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpStateDetailsNotFoundException;
import com.github.sujankumarmitra.otpservice.model.v1.Otp;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;

import java.util.Optional;

/**
 * This interface is responsible for managing {@link OtpStatusDetails} objects.
 *
 * @author skmitra
 * @version 1
 * @see OtpStatusDetails
 */
public interface OtpStateDetailsDao {

    /**
     * Inserts a new {@link OtpStatusDetails} in the collection
     *
     * @param stateDetails new Otp StateDetails
     * @throws OtpStateDetailsAlreadyExistsException if an attempt is made to insert an {@link OtpStatusDetails}
     *                                               with otpId which is already present in the collection
     * @throws OtpNotFoundException                  if an attempt is made to insert an {@link OtpStatusDetails} with dangling {@link OtpStatusDetails#getOtpId()}
     */
    void insertStateDetails(OtpStatusDetails stateDetails) throws OtpStateDetailsAlreadyExistsException, OtpNotFoundException;

    /**
     * Fetches an {@link OtpStatusDetails} from the collection.
     * If any {@link OtpStatusDetails} exists then it is wrapped in an instance of {@link Optional},
     * otherwise an {@link Optional#empty()} is returned
     *
     * @param otpId the id of the {@link Otp}
     * @return an instance of {@link Optional} with the possibility of containing {@link OtpStatusDetails}
     * @apiNote {@link Optional} may be empty, but never null
     */
    Optional<OtpStatusDetails> getStateDetails(String otpId);

    /**
     * Updates an existing {@link OtpStatusDetails}.
     * The {@link OtpStatusDetails#getOtpId()} is used to search the existing details in the collection.
     *
     * @param stateDetails the stateDetails with updated value
     * @throws OtpStateDetailsNotFoundException if no {@link OtpStatusDetails} is found with {@link OtpStatusDetails#getOtpId()}
     */
    void updateStateDetails(OtpStatusDetails stateDetails) throws OtpStateDetailsNotFoundException;

    /**
     * Updates the {@link OtpStatusDetails#getTotalVerificationAttemptsMade()} for an Otp
     *
     * @param otpId        the id of the Otp
     * @param attemptsMade total attempts made
     */
    void setTotalVerificationAttemptsMade(String otpId, long attemptsMade) throws OtpStateDetailsNotFoundException;

    /**
     * Updates the {@link OtpStatusDetails} current state
     *
     * @param otpId        the id of the otp
     * @param newState     new state
     * @param reasonPhrase reason for state change
     */
    void setState(String otpId, OtpStatus newState, String reasonPhrase) throws OtpStateDetailsNotFoundException;
}