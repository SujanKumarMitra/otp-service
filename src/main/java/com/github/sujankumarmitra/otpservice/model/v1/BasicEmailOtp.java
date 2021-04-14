package com.github.sujankumarmitra.otpservice.model.v1;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class BasicEmailOtp extends AbstractEmailOtp implements EmailOtp {

    private final String id;
    private final String code;
    private final Instant createdAt;
    private final Duration durationBeforeExpiry;
    private final String emailAddress;
    private final String messageBody;

    public BasicEmailOtp(
            String id,
            String code,
            Instant createdAt,
            Duration durationBeforeExpiry,
            String emailAddress,
            String messageBody) throws NullPointerException {

        Objects.requireNonNull(id);
        Objects.requireNonNull(code);
        Objects.requireNonNull(createdAt);
        Objects.requireNonNull(durationBeforeExpiry);
        Objects.requireNonNull(emailAddress);
        Objects.requireNonNull(messageBody);

        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.durationBeforeExpiry = durationBeforeExpiry;
        this.emailAddress = emailAddress;
        this.messageBody = messageBody;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Duration getDurationBeforeExpiry() {
        return durationBeforeExpiry;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }

    public static EmailOtp.Builder<EmailOtp> newBuilder() {
        return new BasicEmailOtpBuilder();
    }

    public static class BasicEmailOtpBuilder implements EmailOtp.Builder<EmailOtp> {
        private String id;
        private String code;
        private Instant createdAt;
        private Duration durationBeforeExpiry;
        private String emailAddress;
        private String messageBody;


        @Override
        public Builder<EmailOtp> withId(String id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder<EmailOtp> withCode(String code) {
            this.code = code;
            return this;
        }

        @Override
        public Builder<EmailOtp> withCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Override
        public Builder<EmailOtp> withCreatedAtNow() {
            this.createdAt = Instant.now();
            return this;
        }

        @Override
        public Builder<EmailOtp> withDurationBeforeExpiry(Duration durationBeforeExpiry) {
            this.durationBeforeExpiry = durationBeforeExpiry;
            return this;
        }

        @Override
        public Builder<EmailOtp> withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        @Override
        public Builder<EmailOtp> withMessageBody(String messageBody) {
            this.messageBody = messageBody;
            return this;
        }

        @Override
        public EmailOtp build() throws NullPointerException {
            return new BasicEmailOtp(
                    id,
                    code,
                    createdAt,
                    durationBeforeExpiry,
                    emailAddress,
                    messageBody);
        }
    }
}
