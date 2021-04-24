package com.github.sujankumarmitra.otpservice.configuration.v1;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Otp Service Application")
                        .description("Spring Boot Application featuring OTP as a Service")
                        .version("v1.0.0")
                );

    }
}
