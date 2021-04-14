package com.github.sujankumarmitra.otpservice.configuration.v1;

/**
 * This class contains configuration properties related to email purposes.
 *
 * @author skmitra
 * @version 1
 */
public interface EmailProperties {

    /**
     * Represents the subject which will be set while sending mails.
     *
     * @return the subject of email
     */
    String getEmailSubject();
}
