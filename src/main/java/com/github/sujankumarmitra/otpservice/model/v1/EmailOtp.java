package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This interface represents that otp is to be sent to user by email.
 */
public interface EmailOtp extends Otp {

    /**
     * Represents the email address to which the otp is to be sent.
     *
     * @return the email address
     */
    String getEmailAddress();

    /**
     * Returns the message which is to be sent to the user.
     *
     * @return the message to send or body of the email
     * @apiNote <b>The message is expected to contain the otp-code.</b>
     */
    String getMessageBody();
}