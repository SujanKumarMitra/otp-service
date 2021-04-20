package com.github.sujankumarmitra.otpservice.model.v1;

public class JacksonCompatibleErrorResponse implements ErrorResponse {
    private String message;

    public JacksonCompatibleErrorResponse() {
    }

    public JacksonCompatibleErrorResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
