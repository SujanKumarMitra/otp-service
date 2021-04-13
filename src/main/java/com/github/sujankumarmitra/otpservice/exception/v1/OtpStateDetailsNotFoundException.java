package com.github.sujankumarmitra.otpservice.exception.v1;

import com.github.sujankumarmitra.otpservice.dao.v1.OtpStateDetailsDao;
import com.github.sujankumarmitra.otpservice.model.v1.OtpStateDetails;

/**
 * This exception is thrown when any operation(except create) is attempted on an non-existing {@link OtpStateDetails}
 *
 * @author skmitra
 * @version 1
 * @see OtpStateDetailsDao#updateStateDetails(OtpStateDetails)
 */
public class OtpStateDetailsNotFoundException extends RuntimeException {
    public OtpStateDetailsNotFoundException(String otpId) {
        super("OtpStateDetails for optId='" + otpId + "' not found");
    }

}