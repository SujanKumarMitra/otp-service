package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleCreateEmailOtpRequest implements CreateEmailOtpRequest {

    private String emailAddress;
    private String messageTemplate;
    private long expiryTimeInSeconds;

    public JacksonCompatibleCreateEmailOtpRequest() {
    }

    public JacksonCompatibleCreateEmailOtpRequest(String emailAddress,
                                                  String messageTemplate,
                                                  long expiryTimeInSeconds) {
        this.emailAddress = emailAddress;
        this.messageTemplate = messageTemplate;
        this.expiryTimeInSeconds = expiryTimeInSeconds;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @Override
    public long getExpiryTimeInSeconds() {
        return expiryTimeInSeconds;
    }

    public void setExpiryTimeInSeconds(long expiryTimeInSeconds) {
        this.expiryTimeInSeconds = expiryTimeInSeconds;
    }

    @Override
    public String toString() {
        return "CreateEmailOtpRequest{" +
                "emailAddress='" + emailAddress + '\'' +
                ", messageTemplate='" + messageTemplate + '\'' +
                ", expiryTimeInSeconds=" + expiryTimeInSeconds +
                '}';
    }
}
