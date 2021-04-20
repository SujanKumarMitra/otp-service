package com.github.sujankumarmitra.otpservice.model.v1;

import javax.validation.constraints.NotBlank;

public class JacksonCompatibleOtpVerificationRequest extends AbstractOtpVerificationRequest implements OtpVerificationRequest {

    @NotBlank(message = "otpId can't be blank")
    private String otpId;
    @NotBlank(message = "otpCode can't be blank")
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
