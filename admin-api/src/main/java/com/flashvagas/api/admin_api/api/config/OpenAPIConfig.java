package com.flashvagas.api.admin_api.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI taskServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Flashvagas Admin API")
                        .description("This is the REST API for Flashvagas")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}