package com.gateway.api.gateway_api.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private final String[] freeResourceUrls = { "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**", "/api-docs/**", "/aggregate/**", "/actuator/prometheus",
            "/.well-known/acme-challenge/", "/api/v1/webhook" };

    @Value("${frontend.service.url}")
    String frontEndUrl;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(freeResourceUrls)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration apiCorsConfig = new CorsConfiguration();
        apiCorsConfig.setAllowedOrigins(List.of(frontEndUrl));
        apiCorsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        apiCorsConfig.setAllowedHeaders(List.of("*"));
        apiCorsConfig.setAllowCredentials(true);

        CorsConfiguration actuatorCorsConfig = new CorsConfiguration();
        actuatorCorsConfig.setAllowedOrigins(List.of(frontEndUrl));
        actuatorCorsConfig.setAllowedMethods(List.of("GET", "OPTIONS"));
        actuatorCorsConfig.setAllowedHeaders(List.of("*"));
        actuatorCorsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", apiCorsConfig);
        source.registerCorsConfiguration("/actuator/**", actuatorCorsConfig);

        return source;
    }
}
