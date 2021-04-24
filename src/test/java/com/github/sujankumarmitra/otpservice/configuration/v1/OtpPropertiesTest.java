package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OtpPropertiesTest {

    public static final String REPLACEMENT = "RePlAcEd";

    @Configuration
    @EnableConfigurationProperties(ValidatableOtpProperties.class)
    static class Config {
    }

    @Autowired
    OtpProperties properties;

    @Test
    void testPropertiesShouldNotBeNull() {
        assertNotNull(properties);
    }

    @Test
    void givenProperties_shouldHaveCodePlaceholderRegexInDefaultMessageTemplate() {
        String messageTemplate = properties.getDefaultMessageTemplate();
        String regex = properties.getMessageTemplateCodePlaceholderRegex();
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(messageTemplate);
        assertTrue(matcher.find());

        String replacedMessageTemplate = messageTemplate.replaceAll(regex, REPLACEMENT);

        assertTrue(replacedMessageTemplate.contains(REPLACEMENT));
    }

    @Test
    void testTotalVerificationAttemptsShouldNotBeNegative() {
        assertTrue(properties.getTotalAllowedAttempts() > 0);
    }
}