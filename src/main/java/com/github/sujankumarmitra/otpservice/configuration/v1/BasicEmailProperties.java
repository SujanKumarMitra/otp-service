package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Implementation of {@link EmailProperties}.
 * This class is annotated with {@link ConfigurationProperties} for property injection
 *
 * @author skmitra
 * @version 1
 */
@ConfigurationProperties(prefix = "email")
public class BasicEmailProperties implements EmailProperties {

    private String emailSubject;

    public BasicEmailProperties() {
    }

    public BasicEmailProperties(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public String toString() {
        return "EmailProperties{" +
                "emailSubject='" + emailSubject + '\'' +
                '}';
    }
}
