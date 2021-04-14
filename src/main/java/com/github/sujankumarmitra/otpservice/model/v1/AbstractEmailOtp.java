package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This class provides some standard implementations,
 * which all {@link EmailOtp} object must comply.
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
public abstract class AbstractEmailOtp implements EmailOtp {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailOtp)) return false;

        EmailOtp that = (EmailOtp) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getCode() != null ? !getCode().equals(that.getCode()) : that.getCode() != null) return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() != null)
            return false;
        if (getDurationBeforeExpiry() != null ? !getDurationBeforeExpiry().equals(that.getDurationBeforeExpiry()) : that.getDurationBeforeExpiry() != null)
            return false;
        if (getEmailAddress() != null ? !getEmailAddress().equals(that.getEmailAddress()) : that.getEmailAddress() != null)
            return false;
        return getMessageBody() != null ? getMessageBody().equals(that.getMessageBody()) : that.getMessageBody() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        result = 31 * result + (getDurationBeforeExpiry() != null ? getDurationBeforeExpiry().hashCode() : 0);
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        result = 31 * result + (getMessageBody() != null ? getMessageBody().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmailOtp{" +
                "id='" + getId() + '\'' +
                ", code='" + getCode() + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", durationBeforeExpiry=" + getDurationBeforeExpiry() +
                ", emailAddress='" + getEmailAddress() + '\'' +
                ", messageBody='" + getMessageBody() + '\'' +
                '}';
    }
}
