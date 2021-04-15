package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleOtpVerificationRequest extends AbstractOtpVerificationRequest implements OtpVerificationRequest {

    private String otpId;
    private String otpCode;

    public JacksonCompatibleOtpVerificationRequest() {
    }

    public JacksonCompatibleOtpVerificationRequest(String otpId, String otpCode) {
        this.otpId = otpId;
        this.otpCode = otpCode;
    }

    @Override
    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    @Override
    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}
