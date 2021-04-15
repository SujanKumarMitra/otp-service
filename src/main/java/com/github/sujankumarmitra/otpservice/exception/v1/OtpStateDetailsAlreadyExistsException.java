package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;

/**
 * This exception is thrown when an attempt to insert an existing {@link OtpStatusDetails} is made.
 *
 * @author skmitra
 * @version 1
 * @see OtpStatusDetailsDao#insertStatusDetails(OtpStatusDetails)
 */
public class OtpStateDetailsAlreadyExistsException extends RuntimeException {
    /**
     * @param otpId the id of the otp
     */
    public OtpStateDetailsAlreadyExistsException(String otpId) {
        super("OtpStateDetails of otpId='" + otpId + "' already exists!");
    }
}