package dev.ritam.authorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ritam.authorization.configuration.PropertyValuesConfiguration;
import dev.ritam.authorization.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends UsernamePasswordAuthenticationFilter {
    private static final int TOKEN_EXPIRATION_IN_MINUTE = 30;
    private final AuthenticationManager authenticationManager;
    private final PropertyValuesConfiguration propertyValuesConfiguration;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            var errMsg = "AuthorizationFilter.attemptAuthentication(HttpServletRequest request, HttpServletResponse response) : " +
                    "Bad request";
            log.error(errMsg);
            throw new BadRequestException(errMsg);
        }

        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Object principal = authResult.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (username != null) {
            final var now = Instant.now();
            var algorithm = Algorithm.HMAC512(
                    propertyValuesConfiguration
                            .getSecretKey()
                            .getBytes(StandardCharsets.UTF_8)
            );
            String token = JWT.create()
                    .withSubject(username)
                    .withIssuedAt(Date.from(now))
                    .withExpiresAt(Date.from(now.plus(TOKEN_EXPIRATION_IN_MINUTE, ChronoUnit.MINUTES)))
                    .sign(algorithm);
            Map<String, String> tokenValue = new HashMap<>();
            tokenValue.put("access_token", token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokenValue);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        Map<String, String> error = new HashMap<>();
        error.put("error", failed.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
