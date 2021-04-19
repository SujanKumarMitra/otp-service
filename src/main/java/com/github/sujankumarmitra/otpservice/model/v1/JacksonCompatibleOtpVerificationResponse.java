package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleOtpVerificationResponse extends AbstractOtpVerificationResponse implements OtpVerificationResponse {
    private boolean verified;
    private OtpStatus status;
    private String message;
    private long attemptsRemaining;

    public JacksonCompatibleOtpVerificationResponse() {
    }

    public JacksonCompatibleOtpVerificationResponse(boolean verified,
                                                    OtpStatus status,
                                                    String message,
                                                    long attemptsRemaining) {
        this.verified = verified;
        this.status = status;
        this.message = message;
        this.attemptsRemaining = attemptsRemaining;
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
