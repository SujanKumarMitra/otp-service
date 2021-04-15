package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleOtpVerificationResponse extends AbstractOtpVerificationResponse implements OtpVerificationResponse {
    private boolean verified;
    private OtpStatus status;
    private String message;
    private long attemptsRemaining;

    public JacksonCompatibleOtpVerificationResponse(boolean verified) {
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public OtpStatus getStatus() {
        return status;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public void setAttemptsRemaining(long attemptsRemaining) {
        this.attemptsRemaining = attemptsRemaining;
    }


}
