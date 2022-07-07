package dev.ritam.authorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.ritam.authorization.configuration.PropertyValuesConfiguration;
import dev.ritam.authorization.exception.InvalidJWTException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    PropertyValuesConfiguration propertyValuesConfiguration;
    @InjectMocks
    AuthenticationFilter authenticationFilter;

    @Test
    void doFilterInternalTest1() throws ServletException, IOException {
        // When
        Mockito.when(request.getServletPath())
                .thenReturn("/authorization/api/login");
        authenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        Mockito.verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void doFilterInternalTest2() throws ServletException, IOException {
        // When
        Mockito.when(request.getServletPath())
                .thenReturn("/authorization/api/signup");
        authenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        Mockito.verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void doFilterInternalTest3() {
        // When
        Mockito.when(request.getServletPath())
                .thenReturn("test");
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("bad_token");

        // Then
        var errorMsg = "AuthenticationFilter.doFilterInternal " +
                "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : " +
                "Bad token";
        Assertions.assertThrows(
                InvalidJWTException.class,
                () -> authenticationFilter.doFilterInternal(request, response, filterChain),
                errorMsg
        );
    }

    @Test
    void doFilterInternalTest4() throws ServletException, IOException {
        // Given
        Mockito.when(propertyValuesConfiguration.getSecretKey())
                .thenReturn("secret");
        String customerEmail = "ritam@gmail.com";
        final String SECRET_KEY = propertyValuesConfiguration.getSecretKey();
        final int TOKEN_EXPIRATION_IN_MINUTE = 5;
        final var now = Instant.now();
        var algorithm = Algorithm.HMAC512(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String token = JWT.create()
                .withSubject(customerEmail)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(TOKEN_EXPIRATION_IN_MINUTE, ChronoUnit.MINUTES)))
                .sign(algorithm);

        // When
        Mockito.when(request.getServletPath())
                .thenReturn("test");
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer " + token);
        authenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        MatcherAssert.assertThat(username, Matchers.notNullValue());
        MatcherAssert.assertThat(username, Matchers.is(customerEmail));
    }

    @Test
    void doFilterInternalTestWithBadToken() {
        // When
        Mockito.when(request.getServletPath())
                .thenReturn("test");
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer bad_token");
        Mockito.when(propertyValuesConfiguration.getSecretKey())
                .thenReturn("secret");

        // Then

        Assertions.assertThrows(
                InvalidJWTException.class,
                () -> authenticationFilter.doFilterInternal(request, response, filterChain)
        );
    }

    @Test
    void doFilterInternalTestWithExpiredToken() {
        // When
        Mockito.when(request.getServletPath())
                .thenReturn("test");
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                        ".eyJzdWIiOiJyaXRhbUBnbWFpbC5jb20iLCJleHAiOjE2NTIxMTM4OTAsImlhdCI6MTY1MjExMjA5MH0" +
                        ".8eT7jh3sR2sM3rYDMVIsCaH_gdVZ6BCSPCYZ71SOhm1vvxokw9oQDqnSyhry9izs4UQhUlBO8SwtVbRmbz0X3A");
        Mockito.when(propertyValuesConfiguration.getSecretKey())
                .thenReturn("secret");

        // Then
        Assertions.assertThrows(
                InvalidJWTException.class,
                () -> authenticationFilter.doFilterInternal(request, response, filterChain)
        );
    }
}
