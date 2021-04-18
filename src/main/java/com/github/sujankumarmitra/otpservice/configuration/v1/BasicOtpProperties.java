package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Implementation of {@link OtpProperties}.
 * This class is annotated with {@link ConfigurationProperties} for property injection
 *
 * @author skmitra
 * @version 1
 */
@ConfigurationProperties(prefix = "otp")
public class BasicOtpProperties implements OtpProperties {

    private String defaultMessageTemplate;
    private String messageTemplateCodePlaceholderRegex;
    private long totalAllowedAttempts;

    public BasicOtpProperties() {
    }

    public BasicOtpProperties(
            String defaultMessageTemplate,
            String messageTemplateCodePlaceholderRegex,
            long totalAllowedAttempts) {
        this.defaultMessageTemplate = defaultMessageTemplate;
        this.messageTemplateCodePlaceholderRegex = messageTemplateCodePlaceholderRegex;
        this.totalAllowedAttempts = totalAllowedAttempts;
    }


    @Override
    public String getDefaultMessageTemplate() {
        return defaultMessageTemplate;
    }

    public void setDefaultMessageTemplate(String defaultMessageTemplate) {
        this.defaultMessageTemplate = defaultMessageTemplate;
    }

    @Override
    public String getMessageTemplateCodePlaceholderRegex() {
        return messageTemplateCodePlaceholderRegex;
    }

    public void setMessageTemplateCodePlaceholderRegex(String messageTemplateCodePlaceholderRegex) {
        this.messageTemplateCodePlaceholderRegex = messageTemplateCodePlaceholderRegex;
    }

    @Override
    public long getTotalAllowedAttempts() {
        return totalAllowedAttempts;
    }

    public void setTotalAllowedAttempts(long totalAllowedAttempts) {
        this.totalAllowedAttempts = totalAllowedAttempts;
    }

    @Override
    public String toString() {
        return "OtpProperties{" +
                "defaultMessageTemplate='" + defaultMessageTemplate + '\'' +
                ", messageTemplateCodePlaceholderRegex='" + messageTemplateCodePlaceholderRegex + '\'' +
                ", totalAllowedAttempts=" + totalAllowedAttempts +
                '}';
    }
}
