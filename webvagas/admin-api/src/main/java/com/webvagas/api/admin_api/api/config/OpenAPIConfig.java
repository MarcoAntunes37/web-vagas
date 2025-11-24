package com.webvagas.api.admin_api.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI AdminApi() {
        return new OpenAPI()
                .info(new Info().title("Webvagas Admin API")
                        .description("This is the REST API for Webvagas Admin")
                       .version("v1.0.0"));
    }
}