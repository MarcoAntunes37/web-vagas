package com.webvagas.api.gateway_api.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
@SuppressWarnings("deprecation")
public class Routes {
        @Value("${webvagas-api.service.url}")
        private String webvagasApiUrl;

        @Value("${admin-api.service.url}")
        private String adminApiUrl;

        @Bean
        public RouterFunction<ServerResponse> webVagasApiRouter() {
                return GatewayRouterFunctions.route("webvagas_api")
                                .route(RequestPredicates.path("/api/v1/**"),
                                                HandlerFunctions.http(webvagasApiUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("webVagasApiCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> webVagasApiSwaggerRouter() {
                return GatewayRouterFunctions.route("webvagas_api_swagger")
                                .route(RequestPredicates.path("/aggregate/webvagas-api/v3/api-docs"),
                                                HandlerFunctions.http(webvagasApiUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "webVagasApiSwaggerCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .filter(setPath("/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> adminApiRouter() {
                return GatewayRouterFunctions.route("admin_api")
                                .route(RequestPredicates.path("/api/v1/**"),
                                                HandlerFunctions.http(adminApiUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("adminApiCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> adminApiSwaggerRouter() {
                return GatewayRouterFunctions.route("webvagas_api_swagger")
                                .route(RequestPredicates.path("/aggregate/admin-api/v3/api-docs"),
                                                HandlerFunctions.http(adminApiUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "adminApiSwaggerCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .filter(setPath("/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> fallbackRoute() {
                return route("fallbackRoute")
                                .route(RequestPredicates.path("/fallbackRoute"),
                                                request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                                                .body("Service Unavailable, please try again later"))
                                .build();
        }
}