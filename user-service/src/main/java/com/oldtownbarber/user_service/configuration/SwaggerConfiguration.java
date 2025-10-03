package com.oldtownbarber.user_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Old Town Barber - User Service API")
                        .description("Microservicio para la gestión de usuarios (registro, autenticación, actualización de perfiles)")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Old Town Barber Team")
                                .email("support@oldtownbarber.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}

