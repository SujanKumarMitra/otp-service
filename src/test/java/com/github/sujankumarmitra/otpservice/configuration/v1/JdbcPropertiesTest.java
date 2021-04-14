package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JdbcPropertiesTest {

    @Autowired
    protected JdbcProperties properties;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void givenPropertiesShouldNotBeNull() {
        assertNotNull(properties);
        logger.info("Properties {}", properties);
    }

    @Test
    void givenProperties_whenTryConnectToDb_shouldConnectToDb() throws SQLException {
        Connection connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        assertNotNull(connection);
    }

}