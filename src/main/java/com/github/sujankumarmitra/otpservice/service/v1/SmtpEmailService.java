package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService implements EmailService {

    private MailSender mailSender;

    @Autowired
    public SmtpEmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) throws EmailMessagingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(emailMessage.getSubject());
        simpleMailMessage.setText(emailMessage.getBody());
        emailMessage.getRecipients().forEach(simpleMailMessage::setTo);

        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new EmailMessagingException(e);
        }
    }
}