package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of {@link OtpProperties}.
 * This class is annotated with {@link ConfigurationProperties} for property injection
 *
 * @author skmitra
 * @version 1
 */
@ConfigurationProperties(prefix = "otp")
@Validated
public class ValidatableOtpProperties implements OtpProperties, Validator {

    @NotBlank
    private String defaultMessageTemplate;
    @NotBlank
    private String messageTemplateCodePlaceholderRegex;
    @Min(1)
    private long totalAllowedAttempts;

    public ValidatableOtpProperties() {
    }

    public ValidatableOtpProperties(
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

    @Override
    public boolean supports(Class<?> clazz) {
        return getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern pattern = Pattern.compile(messageTemplateCodePlaceholderRegex);
        Matcher matcher = pattern.matcher(defaultMessageTemplate);
        if(!matcher.find()) {
            errors.rejectValue(
                    "defaultMessageTemplate",
                    "MessageTemplate does not contain otp placeholder");
        }
    }
}
