package com.github.sujankumarmitra.otpservice.configuration.v1;


/**
 * This interface contains configuration properties for JDBC
 *
 * @author skmitra
 * @version 1
 */
public interface JdbcProperties {

    String getUrl();

    String getUsername();

    String getPassword();
}
