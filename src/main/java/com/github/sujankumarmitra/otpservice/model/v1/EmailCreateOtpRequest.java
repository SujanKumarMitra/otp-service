package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This interface represents that otp code is to be sent to an email address.
 *
 * @author skmitra
 * @version 1
 * @see CreateOtpRequest
 */
public interface EmailCreateOtpRequest extends CreateOtpRequest {

    /**
     * Represents the email address to which this otp should be sent
     *
     * @return
     */
    String getEmailToBeSent();
}
