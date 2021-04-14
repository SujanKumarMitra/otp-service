package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This class provides some standard implementations,
 * which all {@link EmailMessage} object must comply.
 * These implementations include:
 * <ul>
 *     <li>{@link Object#equals(Object)}</li>
 *     <li>{@link Object#hashCode()}</li>
 *     <li>{@link Object#toString()}</li>
 * </ul>
 *
 * @author skmitra
 * @version 1
 */
public abstract class AbstractEmailMessage implements EmailMessage {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailMessage)) return false;

        EmailMessage that = (EmailMessage) o;

        if (getRecipients() != null ? getRecipients().equals(that.getRecipients()) : that.getRecipients() != null)
            return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        return getBody() != null ? getBody().equals(that.getBody()) : that.getBody() == null;
    }

    @Override
    public int hashCode() {
        int result = getRecipients() != null ? getRecipients().hashCode() : 0;
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "recipients=" + getRecipients() +
                ", subject='" + getSubject() + '\'' +
                ", body='" + getBody() + '\'' +
                '}';
    }
}
