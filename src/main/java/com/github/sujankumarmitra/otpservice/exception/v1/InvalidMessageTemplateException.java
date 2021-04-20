package com.github.sujankumarmitra.otpservice.exception.v1;

public class InvalidMessageTemplateException extends RuntimeException {
    public InvalidMessageTemplateException(String regex) {
        super(regex + " missing from message template");
    }
}
