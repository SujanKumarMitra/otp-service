package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;

public interface EmailService {

    void sendEmail(EmailMessage message) throws EmailMessagingException;
}
