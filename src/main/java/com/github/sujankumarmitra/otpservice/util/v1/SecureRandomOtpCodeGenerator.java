package com.github.sujankumarmitra.otpservice.util.v1;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.PrimitiveIterator;

@Component
public class SecureRandomOtpCodeGenerator implements OtpCodeGenerator {

    private static final char[] LOWER_CASE_ALPHABETS = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
    private static final char[] UPPER_CASE_ALPHABETS = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
    private static final char[] NUMBERS = "1234567890".toCharArray();
    private static final char[] SPECIAL_CHARS = "~!@#$%&".toCharArray();

    private SecureRandom random;
    private PrimitiveIterator.OfInt lowerCaseCharsIndexIterator;
    private PrimitiveIterator.OfInt upperCaseCharsIndexIterator;
    private PrimitiveIterator.OfInt numbersIndexIterator;
    private PrimitiveIterator.OfInt specialCharsIndexIterator;

    public SecureRandomOtpCodeGenerator() {
        random = new SecureRandom();

        lowerCaseCharsIndexIterator = random.ints(0, LOWER_CASE_ALPHABETS.length).iterator();
        upperCaseCharsIndexIterator = random.ints(0, UPPER_CASE_ALPHABETS.length).iterator();
        numbersIndexIterator = random.ints(0, NUMBERS.length).iterator();
        specialCharsIndexIterator = random.ints(0, SPECIAL_CHARS.length).iterator();
    }

    @Override
    public String generateNewOtpCode() {
        final StringBuilder code = new StringBuilder();

        code.append(UPPER_CASE_ALPHABETS[upperCaseCharsIndexIterator.next()]);
        code.append(SPECIAL_CHARS[specialCharsIndexIterator.next()]);
        code.append(NUMBERS[numbersIndexIterator.next()]);
        code.append(LOWER_CASE_ALPHABETS[lowerCaseCharsIndexIterator.next()]);
        code.append(SPECIAL_CHARS[specialCharsIndexIterator.next()]);
        code.append(NUMBERS[numbersIndexIterator.next()]);

        return code.toString();
    }
}
