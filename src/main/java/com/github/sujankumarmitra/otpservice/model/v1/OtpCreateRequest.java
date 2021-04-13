package com.github.sujankumarmitra.otpservice.model.v1;

import com.github.sujankumarmitra.otpservice.configuration.v1.OtpConfiguration;

/**
 * This interface represents an request to create a new otp.
 *
 * @author skmitra
 * @version 1
 */
public interface OtpCreateRequest {

    /**
     * Represents an message to be sent to the sender with the otp code attached.
     * The message template should contain an regex indicating where the otp-code is to be placed.
     *
     * @return the message
     * @see OtpConfiguration#getMessageTemplateCodePlaceholderRegex()
     */
    String getMessageTemplate();

    /**
     * Represents the no. of seconds after the creation of otp,
     * following which the otp will become invalid.
     *
     * @return expiration time in seconds.
     */
    long getExpiryTimeInSeconds();
}