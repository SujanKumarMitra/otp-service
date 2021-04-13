package com.github.sujankumarmitra.otpservice.util.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class OtpCodeGeneratorTest {
    protected OtpCodeGenerator generatorUnderTest;
    protected Logger logger;

    @BeforeEach
    void setUp() {
        assertNotNull(generatorUnderTest, () -> "generator can't be null");
        assertNotNull(logger, () -> "logger can't be null");
    }

    @Test
    void givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonNullCode() {
        String otpCode = generatorUnderTest.generateNewOtpCode();
        assertNotNull(otpCode, () -> "OtpCode can't be null");
        logger.info("OtpCode = {}", otpCode);
    }

    @Test
    void givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonEmptyCode() {
        String otpCode = generatorUnderTest.generateNewOtpCode();
        assertNotEquals(0, otpCode.length(), () -> "OtpCode can't be empty");
        logger.info("OtpCode Length = {}", otpCode.length());
    }

}