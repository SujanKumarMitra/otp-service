package com.github.sujankumarmitra.otpservice.model.v1;

import java.util.Objects;

/**
 * @author skmitra
 * @version 1
 */
public class BasicOtpStateDetails implements OtpStateDetails {

    private final String otpId;
    private final OtpState currentState;
    private final String currentStateReasonPhrase;
    private final long totalVerificationAttemptsMade;

    public BasicOtpStateDetails(String otpId,
                                OtpState currentState,
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
    public OtpState getCurrentState() {
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

    public static OtpStateDetails.Builder<OtpStateDetails> newBuilder() {
        return new BasicOtpStateDetailsBuilder();
    }

    public static class BasicOtpStateDetailsBuilder implements OtpStateDetails.Builder<OtpStateDetails> {
        private String otpId;
        private OtpState currentState;
        private String currentStateReasonPhrase;
        private long totalVerificationAttemptsMade;


        @Override
        public Builder<OtpStateDetails> withOtpId(String otpId) {
            this.otpId = otpId;
            return this;
        }

        @Override
        public Builder<OtpStateDetails> withCurrentState(OtpState currentState) {
            this.currentState = currentState;
            return this;
        }

        @Override
        public Builder<OtpStateDetails> withCurrentStateReasonPhrase(String reasonPhrase) {
            currentStateReasonPhrase = reasonPhrase;
            return this;
        }

        @Override
        public Builder<OtpStateDetails> withTotalVerificationAttemptsMade(long attempts) {
            totalVerificationAttemptsMade = attempts;
            return this;
        }

        @Override
        public OtpStateDetails build() throws NullPointerException {
            return new BasicOtpStateDetails(
                    otpId,
                    currentState,
                    currentStateReasonPhrase,
                    totalVerificationAttemptsMade
            );
        }
    }
}
