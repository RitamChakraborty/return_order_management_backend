package dev.ritam.api_gateway.filter;

import dev.ritam.api_gateway.exception.AuthenticationTokenNotFoundException;
import dev.ritam.api_gateway.exception.BadTokenException;
import dev.ritam.api_gateway.model.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private static final String CUSTOMER_EMAIL_HEADER = "x-auth-customer-email";
    private final WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new AuthenticationTokenNotFoundException("Authorization token not found");
        }

        String authorizationHeader =
                Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

        return webClient
                .get()
                .uri("http://localhost:8080/authenticate")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new BadTokenException("Bad token")))
                .bodyToMono(CustomerResponse.class)
                .map(customerResponse -> {
                            exchange
                                    .getRequest()
                                    .mutate()
                                    .header(CUSTOMER_EMAIL_HEADER, customerResponse.getEmail());
                            return exchange;
                        }
                ).flatMap(chain::filter);
    }
}
