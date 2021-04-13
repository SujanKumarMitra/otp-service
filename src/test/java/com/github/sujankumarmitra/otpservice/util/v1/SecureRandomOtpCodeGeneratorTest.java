package com.github.sujankumarmitra.otpservice.util.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecureRandomOtpCodeGeneratorTest extends OtpCodeGeneratorTest {

    @Autowired
    SecureRandomOtpCodeGenerator generator;

    @Override
    @BeforeEach
    void setUp() {
        generatorUnderTest = generator;
        logger = LoggerFactory.getLogger(SecureRandomOtpCodeGeneratorTest.class);
        super.setUp();
    }

    @Override
    @Test
    void givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonEmptyCode() {
        super.givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonEmptyCode();
    }

    @Override
    @Test
    void givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonNullCode() {
        super.givenGenerator_whenGeneratedRandomCode_ShouldGenerateNonNullCode();
    }
}
