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
    String getHost();

    /**
     * @return the smtp port
     */
    int getPort();

    /**
     * @return the smtp username
     */
    String getUsername();

    /**
     * @return the smtp password
     */
    String getPassword();

    /**
     * @return return additional properties related to smtp
     */
    Map<String, String> getProperties();
}
