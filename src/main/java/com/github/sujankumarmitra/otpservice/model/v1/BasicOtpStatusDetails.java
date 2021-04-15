package com.github.sujankumarmitra.otpservice.model.v1;

import java.util.Objects;

/**
 * @author skmitra
 * @version 1
 */
public class BasicOtpStatusDetails implements OtpStatusDetails {

    private final String otpId;
    private final OtpStatus currentState;
    private final String currentStateReasonPhrase;
    private final long totalVerificationAttemptsMade;

    public BasicOtpStatusDetails(String otpId,
                                 OtpStatus currentState,
                                 String currentStateReasonPhrase,
                                 long totalVerificationAttemptsMade) throws NullPointerException {
        Objects.requireNonNull(otpId);
        Objects.requireNonNull(currentState);
        Objects.requireNonNull(currentStateReasonPhrase);

        this.otpId = otpId;
        this.currentState = currentState;
        this.currentStateReasonPhrase = currentStateReasonPhrase;
        this.totalVerificationAttemptsMade = totalVerificationAttemptsMade;
    }

    @Override
    public String getOtpId() {
        return otpId;
    }

    @Override
    public OtpStatus getCurrentStatus() {
        return currentState;
    }

    @Override
    public String getCurrentStateReasonPhrase() {
        return currentStateReasonPhrase;
    }

    @Override
    public long getTotalVerificationAttemptsMade() {
        return totalVerificationAttemptsMade;
    }

    public static OtpStatusDetails.Builder<OtpStatusDetails> newBuilder() {
        return new BasicOtpStateDetailsBuilder();
    }

    public static class BasicOtpStateDetailsBuilder implements OtpStatusDetails.Builder<OtpStatusDetails> {
        private String otpId;
        private OtpStatus currentState;
        private String currentStateReasonPhrase;
        private long totalVerificationAttemptsMade;


        @Override
        public Builder<OtpStatusDetails> withOtpId(String otpId) {
            this.otpId = otpId;
            return this;
        }

        @Override
        public Builder<OtpStatusDetails> withCurrentStatus(OtpStatus currentStatus) {
            this.currentState = currentStatus;
            return this;
        }

        @Override
        public Builder<OtpStatusDetails> withCurrentStateReasonPhrase(String reasonPhrase) {
            currentStateReasonPhrase = reasonPhrase;
            return this;
        }

        @Override
        public Builder<OtpStatusDetails> withTotalVerificationAttemptsMade(long attempts) {
            totalVerificationAttemptsMade = attempts;
            return this;
        }

        @Override
        public OtpStatusDetails build() throws NullPointerException {
            return new BasicOtpStatusDetails(
                    otpId,
                    currentState,
                    currentStateReasonPhrase,
                    totalVerificationAttemptsMade
            );
        }
    }
}
