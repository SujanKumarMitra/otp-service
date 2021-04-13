package com.github.sujankumarmitra.otpservice.util.v1;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.PrimitiveIterator;

/**
 * {@inheritDoc}
 * <p>
 * This class uses {@link SecureRandom} to generate random otp-codes
 *
 * @author skmitra
 * @version 1
 * @implNote The length of the otp-code is {@code 6}. It consists of alphabets,numbers and special characters.
 */
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

        /*
         * Infinite Iterators which generate random indexes of specified bounds.
         *
         * */
        lowerCaseCharsIndexIterator = random.ints(0, LOWER_CASE_ALPHABETS.length).iterator();
        upperCaseCharsIndexIterator = random.ints(0, UPPER_CASE_ALPHABETS.length).iterator();
        numbersIndexIterator = random.ints(0, NUMBERS.length).iterator();
        specialCharsIndexIterator = random.ints(0, SPECIAL_CHARS.length).iterator();
    }

    @Override
    public String generateNewOtpCode() {
        final StringBuilder code = new StringBuilder();

        /*
         * Append characters from dictionary with random indexes.
         * */
        code.append(UPPER_CASE_ALPHABETS[upperCaseCharsIndexIterator.nextInt()]);
        code.append(SPECIAL_CHARS[specialCharsIndexIterator.nextInt()]);
        code.append(NUMBERS[numbersIndexIterator.nextInt()]);
        code.append(LOWER_CASE_ALPHABETS[lowerCaseCharsIndexIterator.nextInt()]);
        code.append(SPECIAL_CHARS[specialCharsIndexIterator.nextInt()]);
        code.append(NUMBERS[numbersIndexIterator.nextInt()]);

        return code.toString();
    }
}
