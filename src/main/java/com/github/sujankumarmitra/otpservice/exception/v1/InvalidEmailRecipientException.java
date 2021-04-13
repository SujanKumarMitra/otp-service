package com.github.sujankumarmitra.otpservice.exception.v1;

import java.util.StringJoiner;

public class InvalidEmailRecipientException extends EmailMessagingException {

    private final String message;

    public InvalidEmailRecipientException(String... invalidRecipients) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        for (String recipient : invalidRecipients) {
            sj.add(recipient);
        }
        this.message = "Email could not be sent due to invalid recipients = " + sj;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
