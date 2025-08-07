package com.gateway.api.gateway_api.routes;

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
public class Routes {
    @Value("${flashvagas-api.service.url}")
    private String flashvagasApiUrl;

    @Bean
    public RouterFunction<ServerResponse> flashVagasApiRouter() {
        return GatewayRouterFunctions.route("flashvagas_api")
                .route(RequestPredicates.path("/api/v1/**"),
                        HandlerFunctions.http(flashvagasApiUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("flashVagasApiCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> flashVagasApiSwaggerRouter() {
        return GatewayRouterFunctions.route("flashvagas_api_swagger")
                .route(RequestPredicates.path("/aggregate/flashvagas-api/v3/api-docs"),
                        HandlerFunctions.http(flashvagasApiUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "flashVagasApiSwaggerCircuitBreaker",
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