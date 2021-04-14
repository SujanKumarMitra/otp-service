package com.github.sujankumarmitra.otpservice.configuration.v1;


/**
 * This interface contains configuration properties for JDBC
 *
 * @author skmitra
 * @version 1
 */
public interface JdbcProperties {

    /**
     * @return the database connection url
     */
    String getUrl();

    /**
     * @return the database user
     */
    String getUsername();

    /**
     * @return the database password
     */
    String getPassword();
}
