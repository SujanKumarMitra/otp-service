package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Map;
import java.util.Properties;

/**
 * Implementation of {@link SmtpProperties}.
 * This class is annotated with {@link ConfigurationProperties} for property injection
 *
 * @author skmitra
 * @version 1
 */
@ConfigurationProperties("spring.mail")
@Validated
public class ValidatableSmtpProperties implements SmtpProperties, Validator {
    @NotBlank
    private String host;
    @Positive
    private int port;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Map<String, String> properties;

    public ValidatableSmtpProperties() {
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Properties smtpProps = new Properties();
        getProperties().forEach(smtpProps::put);

        Session session = Session.getInstance(smtpProps);
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            errors.reject("SmtpProperties", e.getMessage());
        }
        try {
            transport.connect(host,port,username,password);
        } catch (MessagingException e) {
            errors.reject("SmtpProperties", "Invalid Smtp Credentials!!!");
        }
    }
}
