package dev.ritam.api_gateway.filter;

import dev.ritam.api_gateway.exception.AuthenticationTokenNotFoundException;
import dev.ritam.api_gateway.exception.BadTokenException;
import dev.ritam.api_gateway.model.CustomerResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String CUSTOMER_EMAIL_HEADER = "x-auth-customer-email";
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Autowired
    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new AuthenticationTokenNotFoundException("Authorization token not found");
            }

            String authorizationHeader =
                    Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String authenticationUrl = config.getAuthenticationUrl();

            return webClientBuilder
                    .build()
                    .get()
                    .uri(authenticationUrl + "/authorization/api/authenticate")
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
        };
    }

    @Data
    public static class Config {
        private String authenticationUrl;
    }

}
