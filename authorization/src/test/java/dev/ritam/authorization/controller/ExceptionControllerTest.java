package dev.ritam.authorization.controller;

import dev.ritam.authorization.exception.BadRequestException;
import dev.ritam.authorization.exception.CustomerExistsException;
import dev.ritam.authorization.exception.InvalidJWTException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerTest {
    @Spy
    private ExceptionController exceptionController;

    @Test
    void handleInvalidJWTException() {
        // Given
        String errorMsg = "mock";
        var exception = new InvalidJWTException(errorMsg);

        // When
        ResponseEntity<Error> responseEntity = exceptionController.handleInvalidJWTException(exception);

        // Then
        MatcherAssert.assertThat(responseEntity, Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody(), Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody().getMessage(), Matchers.is(errorMsg));
    }

    @Test
    void handleBadRequestException() {
        // Given
        String errorMsg = "mock";
        var exception = new BadRequestException(errorMsg);

        // When
        ResponseEntity<Error> responseEntity = exceptionController.handleBadRequestException(exception);

        // Then
        MatcherAssert.assertThat(responseEntity, Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody(), Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody().getMessage(), Matchers.is(errorMsg));
    }

    @Test
    void handleEntityNotFoundException() {
        // Given
        String errorMsg = "mock";
        var exception = new EntityNotFoundException(errorMsg);

        // When
        ResponseEntity<Error> responseEntity = exceptionController.handleEntityNotFoundException(exception);

        // Then
        MatcherAssert.assertThat(responseEntity, Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody(), Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody().getMessage(), Matchers.is(errorMsg));
    }

    @Test
    void handleCustomerExistsException() {
        // Given
        String errorMsg = "mock";
        var exception = new CustomerExistsException(errorMsg);

        // When
        ResponseEntity<Error> responseEntity = exceptionController.handleCustomerExistsException(exception);

        // Then
        MatcherAssert.assertThat(responseEntity, Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody(), Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody().getMessage(), Matchers.is(errorMsg));
    }

    @Test
    void handleException() {
        // Given
        String errorMsg = "mock";
        var exception = new Exception(errorMsg);

        // When
        ResponseEntity<Error> responseEntity = exceptionController.handleException(exception);

        // Then
        MatcherAssert.assertThat(responseEntity, Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody(), Matchers.notNullValue());
        MatcherAssert.assertThat(responseEntity.getBody().getMessage(), Matchers.is(errorMsg));
    }
}