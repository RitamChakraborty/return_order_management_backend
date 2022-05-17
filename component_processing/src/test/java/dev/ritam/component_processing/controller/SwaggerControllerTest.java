package dev.ritam.component_processing.controller;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SwaggerControllerTest {
    @InjectMocks
    SwaggerController underTest;

    @Test
    void docs() {
        // Given
        String expected = "redirect:/swagger-ui/";

        // When
        String actual = underTest.docs();

        // Then
        MatcherAssert.assertThat(actual, Matchers.is(expected));
    }
}