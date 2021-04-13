package com.github.sujankumarmitra.otpservice.util.v1;

/**
 * This interface is used for generation of new otp codes
 *
 * @author skmitra
 * @version 1
 */
public interface OtpCodeGenerator {
    /**
     * @return new otp-code
     */
    String generateNewOtpCode();
}