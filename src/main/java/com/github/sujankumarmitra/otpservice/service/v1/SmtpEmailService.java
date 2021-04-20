package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.EmailMessagingException;
import com.github.sujankumarmitra.otpservice.model.v1.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService implements EmailService {

    private MailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);

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
            LOGGER.info("Sending email");
            mailSender.send(simpleMailMessage);
            LOGGER.info("Email sent");
        } catch (MailException e) {
            LOGGER.warn("Error sending email", e);
            throw new EmailMessagingException(e);
        }
    }
}