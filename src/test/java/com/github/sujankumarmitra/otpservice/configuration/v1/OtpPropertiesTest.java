package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OtpPropertiesTest {

    @Configuration
    @EnableConfigurationProperties(BasicOtpProperties.class)
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
        assertTrue(messageTemplate.contains(regex));
    }

    @Test
    void testTotalVerificationAttemptsShouldNotBeNegative() {
        assertTrue(properties.getTotalAllowedAttempts() > 0);
    }
}