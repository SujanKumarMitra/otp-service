package com.github.sujankumarmitra.otpservice.controller.v1;

import com.github.sujankumarmitra.otpservice.exception.v1.InvalidMessageTemplateException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpCreationException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpNotFoundException;
import com.github.sujankumarmitra.otpservice.exception.v1.OtpVerificationException;
import com.github.sujankumarmitra.otpservice.model.v1.*;
import com.github.sujankumarmitra.otpservice.service.v1.EmailOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/otp")
public class EmailOtpController {

    EmailOtpService emailOtpService;

    @Autowired
    public EmailOtpController(EmailOtpService emailOtpService) {
        this.emailOtpService = emailOtpService;
    }

    @PostMapping(path = "/create", params = "type=email")
    public ResponseEntity<CreateOtpResponse> createOtp(@RequestBody @Valid JacksonCompatibleCreateEmailOtpRequest request) {
        CreateOtpResponse createOtpResponse = emailOtpService.createOtp(request);
        return ResponseEntity.ok(createOtpResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(@RequestBody @Valid JacksonCompatibleOtpVerificationRequest request) {
        OtpVerificationResponse response = emailOtpService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{otpId}")
    public ResponseEntity<OtpStatusDetails> getOtpStatus(@PathVariable("otpId") String otpId) {
        OtpStatusDetails otpStatus = emailOtpService.getCurrentOtpStatus(otpId);
        return ResponseEntity.ok(otpStatus);
    }

    @ExceptionHandler(OtpNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleOtpNotFoundException(OtpNotFoundException exception) {
        ErrorResponse response = new JacksonCompatibleErrorResponse(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(OtpCreationException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponse> handleOtpCreationException(OtpCreationException exception) {
        ErrorResponse response = new JacksonCompatibleErrorResponse("Error creating otp. Please try again");
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @ExceptionHandler(InvalidMessageTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidMessageTemplateException(InvalidMessageTemplateException exception) {
        ErrorResponse response = new JacksonCompatibleErrorResponse("MessageTemplate does not contain OtpCode placeholder.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(OtpVerificationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleOtpVerificationException(OtpVerificationException exception) {
        ErrorResponse response = new JacksonCompatibleErrorResponse("Error verifying otp. Please try again");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
