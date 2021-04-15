package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * Represents current state details of an otp transaction
 *
 * @author skmitra
 * @version 1
 * @see OtpStatus
 */
public interface OtpStatusDetails {

    /**
     * @return the otpId associated with this state
     * @see Otp#getId()
     */
    String getOtpId();

    /**
     * @return status enum
     */
    OtpStatus getCurrentStatus();

    /**
     * @return the reason for which the {@link #getCurrentStatus()} is currently at.
     */
    String getCurrentStateReasonPhrase();


    /**
     * @return total no of attempts made for otp-verification
     */
    long getTotalVerificationAttemptsMade();


    /**
     * Builder for constructing {@link OtpStatusDetails}
     *
     * @param <T> type of {@link OtpStatusDetails}
     * @author skmitra
     * @version 1
     */
    interface Builder<T extends OtpStatusDetails> {
        Builder<T> withOtpId(String otpId);

        Builder<T> withCurrentStatus(OtpStatus currentStatus);

        Builder<T> withCurrentStateReasonPhrase(String reasonPhrase);

        Builder<T> withTotalVerificationAttemptsMade(long attempts);

        T build() throws NullPointerException;
    }
}
