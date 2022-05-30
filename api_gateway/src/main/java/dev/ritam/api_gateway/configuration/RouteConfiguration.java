package dev.ritam.api_gateway.configuration;

import dev.ritam.api_gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {
    private final AuthenticationFilter authenticationFilter;

    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(predicateSpec -> predicateSpec
                        .path("/swagger-ui/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .setStatus(HttpStatus.UNAUTHORIZED)
                        )
                        .uri("no://op")
                )
                .route(predicateSpec -> predicateSpec
                        .path("/component-processing/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .filter(authenticationFilter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("forward:/component-processing-fallback")
                                )
                        )
                        .uri("lb://component-processing/")
                )
                .route(predicateSpec -> predicateSpec
                        .path("/authorization/api/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .circuitBreaker(config -> config
                                        .setFallbackUri("forward:/authorization-fallback")
                                )
                        )
                        .uri("lb://authorization/")
                )
                .build();
    }
}
