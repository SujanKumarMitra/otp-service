package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Implementation of {@link EmailProperties}.
 * This class is annotated with {@link ConfigurationProperties} for property injection
 *
 * @author skmitra
 * @version 1
 */
@ConfigurationProperties(prefix = "email")
@Validated
public class ValidatableEmailProperties implements EmailProperties {

    @NotBlank(message = "Email Subject can't be blank")
    private String emailSubject;

    public ValidatableEmailProperties() {
    }

    public ValidatableEmailProperties(String emailSubject) {
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
