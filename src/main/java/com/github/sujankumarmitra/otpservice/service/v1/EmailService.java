package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;
import com.github.sujankumarmitra.otpservice.model.v1.EmailMessage;

public interface EmailService {

    void sendEmail(EmailMessage message) throws EmailMessagingException;
}
