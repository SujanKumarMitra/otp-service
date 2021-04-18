package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleCreateOtpResponse implements CreateOtpResponse {

    private String otpId;

    public JacksonCompatibleCreateOtpResponse() {
    }

    public JacksonCompatibleCreateOtpResponse(String otpId) {
        this.otpId = otpId;
    }

    @Override
    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }
}
