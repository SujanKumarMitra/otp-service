package com.github.sujankumarmitra.otpservice.controlleradvice.v1;

class RequestBodyFieldError {
    private final String fieldName;
    private final String errorMessage;
    private final String rejectedValue;

    public RequestBodyFieldError(String fieldName, String errorMessage, String rejectedValue) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
        this.rejectedValue = rejectedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
}
