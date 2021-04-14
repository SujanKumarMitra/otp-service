package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * Represents current state details of an otp transaction
 *
 * @author skmitra
 * @version 1
 * @see OtpState
 */
public interface OtpStateDetails {

    /**
     * @return the otpId associated with this state
     * @see Otp#getId()
     */
    String getOtpId();

    /**
     * @return state enum
     */
    OtpState getCurrentState();

    /**
     * @return the reason for which the {@link #getCurrentState()} is currently at.
     */
    String getCurrentStateReasonPhrase();


    /**
     * @return total no of attempts made for otp-verification
     */
    long getTotalVerificationAttemptsMade();


    /**
     * Builder for constructing {@link OtpStateDetails}
     *
     * @param <T> type of {@link OtpStateDetails}
     * @author skmitra
     * @version 1
     */
    interface Builder<T extends OtpStateDetails> {
        Builder<T> withOtpId(String otpId);

        Builder<T> withCurrentState(OtpState currentState);

        Builder<T> withCurrentStateReasonPhrase(String reasonPhrase);

        Builder<T> withTotalVerificationAttemptsMade(long attempts);

        T build() throws NullPointerException;
    }
}
