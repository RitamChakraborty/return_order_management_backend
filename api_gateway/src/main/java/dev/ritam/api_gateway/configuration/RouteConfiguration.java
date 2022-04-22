package dev.ritam.api_gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(predicateSpec -> predicateSpec
                        .path("/component-processing/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .circuitBreaker(config -> config
                                        .setFallbackUri("forward:/component-processing-fallback")
                                )
                        )
                        .uri("lb://component-processing/")
                )
                .route(predicateSpec -> predicateSpec
                        .path("/login")
                        .uri("lb://authorization/")
                )
                .build();
    }
}
