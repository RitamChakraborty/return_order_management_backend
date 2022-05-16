package dev.ritam.authorization.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    HandlerExceptionResolver handlerExceptionResolver;
    @InjectMocks
    ExceptionHandlerFilter exceptionHandlerFilter;

    @Test
    void doFilterInternalTest1() throws ServletException, IOException {
        // When
        exceptionHandlerFilter.doFilterInternal(request, response, filterChain);

        // Then
        Mockito.verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void doFilterInternalTest2() throws ServletException, IOException {
        // Given
        Exception exception = new RuntimeException("mock");
        Mockito.doThrow(exception)
                .when(filterChain)
                .doFilter(request, response);

        // When
        exceptionHandlerFilter.doFilterInternal(request, response, filterChain);

        // Then
        Mockito.verify(handlerExceptionResolver)
                .resolveException(request, response, null, exception);
    }
}