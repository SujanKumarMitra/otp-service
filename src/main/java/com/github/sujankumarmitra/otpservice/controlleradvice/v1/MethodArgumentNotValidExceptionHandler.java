package com.github.sujankumarmitra.otpservice.controlleradvice.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<RequestBodyFieldError>> handleException(MethodArgumentNotValidException e) {
        List<RequestBodyFieldError> fieldErrors = e.getFieldErrors()
                .stream()
                .map(this::buildError)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(fieldErrors);
    }

    private RequestBodyFieldError buildError(FieldError error) {
        return new RequestBodyFieldError(
                error.getField(),
                error.getDefaultMessage(),
                error.getRejectedValue().toString());
    }

}
