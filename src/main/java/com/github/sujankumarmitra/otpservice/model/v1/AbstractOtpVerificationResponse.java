package com.github.sujankumarmitra.otpservice.model.v1;

public abstract class AbstractOtpVerificationResponse implements OtpVerificationResponse {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtpVerificationResponse)) return false;

        OtpVerificationResponse that = (OtpVerificationResponse) o;

        if (isVerified() != that.isVerified()) return false;
        if (getAttemptsRemaining() != that.getAttemptsRemaining()) return false;
        if (getStatus() != that.getStatus()) return false;
        return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
    }

    @Override
    public int hashCode() {
        int result = (isVerified() ? 1 : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (int) (getAttemptsRemaining() ^ (getAttemptsRemaining() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OtpVerificationResponse{" +
                "verified=" + isVerified() +
                ", status=" + getStatus() +
                ", message='" + getMessage() + '\'' +
                ", attemptsRemaining=" + getAttemptsRemaining() +
                '}';
    }
}