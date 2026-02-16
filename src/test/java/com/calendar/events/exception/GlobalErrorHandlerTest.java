package com.calendar.events.exception;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalErrorHandlerTest {

    private final GlobalErrorHandler errorHandler = new GlobalErrorHandler();

    @Test
    void handleValidationExceptions_shouldReturnMapOfErrors() {
        WebExchangeBindException ex = mock(WebExchangeBindException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("event", "title", "Title is required");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        Mono<Map<String, String>> result = errorHandler.handleValidationExceptions(ex);

        StepVerifier.create(result)
                .expectNextMatches(errors -> {
                    assertEquals(1, errors.size());
                    assertEquals("Title is required", errors.get("title"));
                    return true;
                })
                .verifyComplete();
    }
}
