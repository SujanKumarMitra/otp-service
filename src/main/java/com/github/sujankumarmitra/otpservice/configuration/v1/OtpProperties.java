package com.github.sujankumarmitra.otpservice.configuration.v1;

import com.github.sujankumarmitra.otpservice.model.v1.Otp;
import com.github.sujankumarmitra.otpservice.model.v1.CreateOtpRequest;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatus;

/**
 * This interface represents the configuration properties related to otp
 *
 * @author skmitra
 * @version 1
 */
public interface OtpProperties {

    /**
     * Represents a default message template which is to be sent to user,
     * if no message template is provided.
     *
     * @return the messageTemplate
     * @apiNote the template must contain a placeholder to place the otp-code
     * @see Otp#getCode()
     * @see CreateOtpRequest#getMessageTemplate()
     * @see #getMessageTemplateCodePlaceholderRegex()
     */
    String getDefaultMessageTemplate();

    /**
     * The message template contains a placeholder where the otp-code will be placed.
     * This regex identifies the placeholder.
     *
     * @return the regex to identify the placeholder
     * @see Otp#getCode()
     * @see #getDefaultMessageTemplate()
     */
    String getMessageTemplateCodePlaceholderRegex();

    /**
     * Represents a total no of attempts allowed on an otp for verification
     * before it becomes {@link OtpStatus#INVALID}
     *
     * @return total allowed attempts allowed
     * @see OtpStatus
     */
    long getTotalAllowedAttempts();
}
