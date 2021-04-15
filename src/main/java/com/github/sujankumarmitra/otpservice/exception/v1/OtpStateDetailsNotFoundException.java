package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.OtpStatusDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStatusDetails;

/**
 * This exception is thrown when any operation(except create) is attempted on an non-existing {@link OtpStatusDetails}
 *
 * @author skmitra
 * @version 1
 * @see OtpStatusDetailsDao#updateStatusDetails(OtpStatusDetails)
 */
public class OtpStateDetailsNotFoundException extends RuntimeException {
    public OtpStateDetailsNotFoundException(String otpId) {
        super("OtpStateDetails for optId='" + otpId + "' not found");
    }

}