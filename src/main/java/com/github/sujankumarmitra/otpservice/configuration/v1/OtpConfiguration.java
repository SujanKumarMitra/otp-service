package com.github.sujankumarmitra.otpservice.configuration.v1;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class for property injection of {@link BasicOtpProperties}
 *
 * @author skmitra
 * @version 1
 */
@Configuration
@EnableConfigurationProperties(BasicOtpProperties.class)
public class OtpConfiguration {
}
