package dev.ritam.authorization.filter;

import dev.ritam.authorization.entity.Customer;
import dev.ritam.authorization.exception.BadRequestException;
import dev.ritam.authorization.model.CustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class AuthorizationFilterTest {
    static final String SECRET_KEY = System.getenv("SECRET_KEY");
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    Authentication authentication;
    @Mock
    AuthenticationException authenticationException;
    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @BeforeEach
    void setUp() {
        Assumptions.assumeTrue(SECRET_KEY != null);
    }

    @Test
    void attemptAuthenticationTest() {
        // When
        Mockito.when(request.getParameter("username"))
                .thenReturn(null);

        // Then
        var errMsg = "AuthorizationFilter.attemptAuthentication(HttpServletRequest request, HttpServletResponse response) : " +
                "Bad request";
        Assertions.assertThrows(
                BadRequestException.class,
                () -> authorizationFilter.attemptAuthentication(request, response),
                errMsg
        );
    }

    @Test
    void attemptAuthenticationTest2() {
        // When
        Mockito.when(request.getParameter("username"))
                .thenReturn("ritam@gmail.com");
        Mockito.when(request.getParameter("password"))
                .thenReturn(null);

        // Then
        var errMsg = "AuthorizationFilter.attemptAuthentication(HttpServletRequest request, HttpServletResponse response) : " +
                "Bad request";
        Assertions.assertThrows(
                BadRequestException.class,
                () -> authorizationFilter.attemptAuthentication(request, response),
                errMsg
        );
    }

    @Test
    void attemptAuthenticationTest3() {
        // Given
        String username = "ritam@gmail.com";
        String password = "password";
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // When
        Mockito.when(request.getParameter("username"))
                .thenReturn(username);
        Mockito.when(request.getParameter("password"))
                .thenReturn(password);
        authorizationFilter.attemptAuthentication(request, response);

        // Then
        Mockito.verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
    }

    @Test
    void successfulAuthenticationTest() throws IOException {
        // Given
        Customer customer = Customer.builder()
                .firstName("Ritam")
                .lastName("Chakraborty")
                .contactNumber("9876543210")
                .email("ritam@gmail.com")
                .password("password")
                .build();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        // When
        Mockito.when(response.getOutputStream())
                .thenReturn(servletOutputStream);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(customer);
        authorizationFilter.successfulAuthentication(request, response, filterChain, authentication);


        // Then
        Mockito.verify(response)
                .setContentType(MediaType.APPLICATION_JSON_VALUE);
        Mockito.verify(response)
                .getOutputStream();
    }

    @Test
    void successfulAuthenticationTest2() throws IOException {
        // Given
        String customerEmail = "ritam@gmail.com";
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        // When
        Mockito.when(response.getOutputStream())
                .thenReturn(servletOutputStream);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(customerEmail);
        authorizationFilter.successfulAuthentication(request, response, filterChain, authentication);

        // Then
        Mockito.verify(response)
                .setContentType(MediaType.APPLICATION_JSON_VALUE);
        Mockito.verify(response)
                .getOutputStream();
    }

    @Test
    void successfulAuthenticationTest3(@Mock CustomerRequest customerRequest) throws IOException {
        // When
        Mockito.when(customerRequest.toString())
                .thenReturn(null);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(customerRequest);
        authorizationFilter.successfulAuthentication(request, response, filterChain, authentication);

        // Then
        Mockito.verify(response, Mockito.never())
                .getOutputStream();
    }

    @Test
    void unsuccessfulAuthentication() throws IOException {
        // Given
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        // When
        Mockito.when(response.getOutputStream())
                .thenReturn(servletOutputStream);
        Mockito.when(authenticationException.getMessage())
                .thenReturn("mock");
        authorizationFilter.unsuccessfulAuthentication(request, response, authenticationException);

        // Then
        Mockito.verify(response)
                .setStatus(HttpStatus.UNAUTHORIZED.value());
        Mockito.verify(response)
                .setContentType(MediaType.APPLICATION_JSON_VALUE);
        Mockito.verify(response)
                .getOutputStream();
    }
}