package com.github.sujankumarmitra.otpservice.model.v1;

public abstract class AbstractOtpVerificationRequest implements OtpVerificationRequest {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtpVerificationRequest)) return false;

        OtpVerificationRequest that = (OtpVerificationRequest) o;

        if (getOtpId() != null ? !getOtpId().equals(that.getOtpId()) : that.getOtpId() != null) return false;
        return getOtpCode() != null ? getOtpCode().equals(that.getOtpCode()) : that.getOtpCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getOtpId() != null ? getOtpId().hashCode() : 0;
        result = 31 * result + (getOtpCode() != null ? getOtpCode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OtpVerificationRequest{" +
                "otpId='" + getOtpId() + '\'' +
                ", otpCode='" + getOtpCode() + '\'' +
                '}';
    }
}
