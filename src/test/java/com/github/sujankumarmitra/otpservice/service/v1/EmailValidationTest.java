package com.github.sujankumarmitra.otpservice.service.v1;

import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This tests prove that, it is not possible to know whether an email address is real or not,
 * cannot be known before sending an email.
 *
 * @author skmitra
 * @version 1
 */
class EmailValidationTest {

    @Test
    void givenInvalidRegexEmail_whenValidated_throwsException() {
        assertThrows(AddressException.class, () -> new InternetAddress("abc@.com"));
    }

    @Test
    void givenValidRegexButNotAvailableAddress_whenValidated_doesNotThrowException() {
        assertDoesNotThrow(()-> new InternetAddress("abc@xyz.com"));
    }
}
