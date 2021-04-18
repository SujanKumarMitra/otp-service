package com.github.sujankumarmitra.otpservice.model.v1;

import java.util.Objects;

/**
 * @author skmitra
 * @version 1
 */
public class BasicOtpStatusDetails implements OtpStatusDetails {

    private final String otpId;
    private final OtpStatus currentStatus;
    private final String currentStatusReasonPhrase;
    private final long totalVerificationAttemptsMade;

    public BasicOtpStatusDetails(String otpId,
                                 OtpStatus currentStatus,
                                 String currentStatusReasonPhrase,
                                 long totalVerificationAttemptsMade) throws NullPointerException {
        Objects.requireNonNull(otpId);
        Objects.requireNonNull(currentStatus);
        Objects.requireNonNull(currentStatusReasonPhrase);

        this.otpId = otpId;
        this.currentStatus = currentStatus;
        this.currentStatusReasonPhrase = currentStatusReasonPhrase;
        this.totalVerificationAttemptsMade = totalVerificationAttemptsMade;
    }

    @Override
    public String getOtpId() {
        return otpId;
    }

    @Override
    public OtpStatus getCurrentStatus() {
        return currentStatus;
    }

    @Override
    public String getCurrentStatusReasonPhrase() {
        return currentStatusReasonPhrase;
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
        private OtpStatus currentStatus;
        private String currentStatusReasonPhrase;
        private long totalVerificationAttemptsMade;


        @Override
        public Builder<OtpStatusDetails> withOtpId(String otpId) {
            this.otpId = otpId;
            return this;
        }

        @Override
        public Builder<OtpStatusDetails> withCurrentStatus(OtpStatus currentStatus) {
            this.currentStatus = currentStatus;
            return this;
        }

        @Override
        public Builder<OtpStatusDetails> withCurrentStatusReasonPhrase(String reasonPhrase) {
            currentStatusReasonPhrase = reasonPhrase;
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
                    currentStatus,
                    currentStatusReasonPhrase,
                    totalVerificationAttemptsMade
            );
        }
    }
}
