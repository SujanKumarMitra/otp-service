package com.github.sujankumarmitra.otpservice.configuration.v1;

import java.util.Map;

/**
 * This interface contains configuration properties
 * for smtp which is used in mailing purpose
 *
 * @author skmitra
 * @version 1
 */
public interface SmtpProperties {

    /**
     * @return the smtp host
     */
    String getSmtpHost();

    /**
     * @return the smtp port
     */
    String getSmtpPort();

    /**
     * @return the smtp username
     */
    String getSmtpUsername();

    /**
     * @return the smtp password
     */
    String getSmtpPassword();

    /**
     * @return return additional properties related to smtp
     */
    Map<String, String> getAdditionalProperties();
}
