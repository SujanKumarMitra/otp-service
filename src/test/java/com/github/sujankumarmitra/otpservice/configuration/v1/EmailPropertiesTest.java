package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailPropertiesTest {

    @Autowired
    EmailProperties properties;

    @Configuration
    @EnableConfigurationProperties(BasicEmailProperties.class)
    static class Config {
    }

    @Test
    void testPropertiesNotNull() {
        assertNotNull(properties);
    }

    @Test
    void testEmailSubjectShouldNotBeNull() {
        assertNotNull(properties.getEmailSubject());
    }
}