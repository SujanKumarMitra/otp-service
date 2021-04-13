package com.github.sujankumarmitra.otpservice.service.v1;

import com.github.sujankumarmitra.otpservice.model.v1.CreateEmailOtpRequest;

/**
 * {@inheritDoc}
 *
 * This interface deals with @{@link com.github.sujankumarmitra.otpservice.model.v1.Otp} which need to be sent to user via an email address.
 *
 * @author skmitra
 * @version 1
 * @see OtpService
 * @see CreateEmailOtpRequest
 */
public interface EmailOtpService extends OtpService<CreateEmailOtpRequest> {
}