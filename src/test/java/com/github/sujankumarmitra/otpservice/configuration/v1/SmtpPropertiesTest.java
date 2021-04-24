package com.github.sujankumarmitra.otpservice.configuration.v1;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import javax.mail.Transport;

import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmtpPropertiesTest {

    @Autowired
    SmtpProperties properties;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Configuration
    @EnableConfigurationProperties(ValidatableSmtpProperties.class)
    static class Config {}


    @Test
    void testPropertiesShouldNotBeNull() {
        assertNotNull(properties);
        logger.info("{}",properties);
    }

    @Test
    void givenSmtpProperties_whenConnect_shouldConnect() {
        Properties props = new Properties();
        Map<String, String> smtpProperties = properties.getProperties();
        smtpProperties.forEach(props::put);

        logger.info("Props = {}", props);

        assertDoesNotThrow(()-> connect(props));

    }

    void connect(Properties props) throws Throwable {
        Session session = Session.getInstance(props);
        Transport transport = session.getTransport("smtp");

        transport.connect(
                properties.getHost(),
                properties.getPort(),
                properties.getUsername(),
                properties.getPassword());

        transport.close();
    }



}
