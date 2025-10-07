package com.oldtownbarber.service_offering.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Old Town Barber - Service Offering API")
                        .description("Microservicio para oferta de servicios para reservas en salones de belleza")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Old Town Barber Team")
                                .email("support@oldtownbarber.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}