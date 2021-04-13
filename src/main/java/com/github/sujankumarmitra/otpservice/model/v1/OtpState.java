package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This enum represents the current state of the otp.
 * The state depicts at which phrase the otp-transaction is currently at.
 *
 * @author skmitra
 * @version 1
 * @apiNote The valid state transitions are:
 * <ul>
 *     {@link #NEW} to {@link #PROCESSING}
 *     {@link #PROCESSING} to {@link #WAITING_FOR_VERIFICATION}
 *     {@link #PROCESSING} to {@link #PROCESSING_ERROR}
 *     {@link #WAITING_FOR_VERIFICATION} to {@link #VERIFIED}
 *     {@link #WAITING_FOR_VERIFICATION} to {@link #EXPIRED}
 *     {@link #WAITING_FOR_VERIFICATION} to {@link #INVALID}
 * </ul>
 */
public enum OtpState {
    /**
     * {@link OtpCreateRequest} is accepted.
     */
    NEW("NEW"),
    /**
     * Otp is being created, otp-code is being sent to user.
     */
    PROCESSING("PROCESSING"),
    /**
     * Otp is successfully sent to user. Now waiting to be verified.
     */
    WAITING_FOR_VERIFICATION("WAITING_FOR_VERIFICATION"),
    /**
     * Error occurred while processing otp. One possible reason could be, otp could not be sent to user
     */
    PROCESSING_ERROR("PROCESSING_ERROR"),
    /**
     * User has successfully verified the otp-code.
     */
    VERIFIED("VERIFIED"),
    /**
     * Otp has expired.
     */
    EXPIRED("EXPIRED"),
    /**
     * Otp has become invalid due to repeated incorrect verification attempts.
     */
    INVALID("INVALID");

    private final String state;

    OtpState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}