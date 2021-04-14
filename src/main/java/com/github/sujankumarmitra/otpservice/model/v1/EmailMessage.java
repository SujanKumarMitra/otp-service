package com.github.sujankumarmitra.otpservice.model.v1;

import java.util.Collection;

/**
 * This interface represents an email which is to be sent
 *
 * @author skmitra
 * @version 1
 * @apiNote the properties must not be {@code null}
 */
public interface EmailMessage {

    /**
     * Represents the recepients of the email
     *
     * @return the recipients
     */
    Collection<String> getRecipients();

    /**
     * Represents the subject of the email
     *
     * @return the subject
     */
    String getSubject();

    /**
     * Represents the email body
     *
     * @return the body
     */
    String getBody();

    /**
     * A Builder class for constructing {@link EmailMessage}s.
     *
     * @param <T> type of email message
     */
    interface Builder<T extends EmailMessage> {
        Builder<T> withSubject(String subject);

        Builder<T> withBody(String body);

        Builder<T> withRecipient(String recipient);

        Builder<T> withRecipients(String... recipients);

        Builder<T> withRecipients(Collection<String> recipients);

        T build() throws NullPointerException;
    }
}