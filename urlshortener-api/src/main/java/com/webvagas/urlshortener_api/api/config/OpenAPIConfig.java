package com.webvagas.urlshortener_api.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "dev", "test" })
public class OpenAPIConfig {
    @Bean
    public OpenAPI API() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shortener API")
                        .description("This is the REST API for Shortener links with webvagas")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}