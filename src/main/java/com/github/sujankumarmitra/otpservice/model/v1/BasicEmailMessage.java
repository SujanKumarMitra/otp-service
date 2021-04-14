package com.github.sujankumarmitra.otpservice.model.v1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author skmitra
 * @version 1
 */
public class BasicEmailMessage implements EmailMessage {

    private final Collection<String> recipients;
    private final String subject;
    private final String body;

    public BasicEmailMessage(Collection<String> recipients, String subject, String body) {
        Objects.requireNonNull(recipients);
        Objects.requireNonNull(subject);
        Objects.requireNonNull(body);

        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public Collection<String> getRecipients() {
        return recipients;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getBody() {
        return body;
    }

    public static EmailMessage.Builder<EmailMessage> newBuilder() {
        return new BasicEmailMessageBuilder();
    }

    /**
     * @author skmitra
     * @version 1
     */
    public static class BasicEmailMessageBuilder implements EmailMessage.Builder<EmailMessage> {
        private Collection<String> recipients;
        private String subject;
        private String body;

        BasicEmailMessageBuilder() {
            recipients = new HashSet<>();
        }

        @Override
        public Builder<EmailMessage> withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        @Override
        public Builder<EmailMessage> withBody(String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder<EmailMessage> withRecipient(String recipient) {
            this.recipients.add(recipient);
            return this;
        }

        @Override
        public Builder<EmailMessage> withRecipients(String... recipients) {
            for (String recipient : recipients) {
                this.recipients.add(recipient);
            }
            return this;
        }

        @Override
        public Builder<EmailMessage> withRecipients(Collection<String> recipients) {
            this.recipients.addAll(recipients);
            return this;
        }

        @Override
        public EmailMessage build() throws NullPointerException {
            return new BasicEmailMessage(this.recipients, this.subject, this.body);
        }
    }
}
