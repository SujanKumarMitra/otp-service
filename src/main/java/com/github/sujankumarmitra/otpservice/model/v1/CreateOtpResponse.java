package com.github.sujankumarmitra.otpservice.model.v1;

/**
 * This interface the expected response for creating a new otp.
 *
 * @author skmitra
 * @version 1
 * @see CreateOtpRequest
 */
public interface CreateOtpResponse {

    /**
     * Returns the id of the otp.
     * Note that the id does not necessary represent the actual otp-code which is to be sent to the user.
     *
     * @return the unique id of otp
     * @apiNote Note that the id does not necessary represent the actual otp-code which is to be sent to the user.
     */
    String getOtpId();

}