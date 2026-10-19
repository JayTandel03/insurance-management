package com.onerivet.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.util.ErrorMessageConstants;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler<Object> handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler<>();
    }

    @Test
    void shouldHandleBadRequest() {
        BadRequestException ex = new BadRequestException("Bad request");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", response.getBody().getError());
    }

    @Test
    void shouldHandleValidation() {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "obj");

        bindingResult.addError(new FieldError("obj", "field", "error message"));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getError().contains("error message"));
    }

    @Test
    void shouldHandleValidation_whenEmptyMessage() {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "obj");

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleValidation(ex);

        assertEquals(ErrorMessageConstants.VALIDATION_FAILED,
                response.getBody().getError());
    }

    @Test
    void shouldHandleNotFound() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("Not found");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getError());
    }

    @Test
    void shouldHandleNoHandler() {
        NoHandlerFoundException ex =
                new NoHandlerFoundException("GET", "/test", null);

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleNoHandler(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorMessageConstants.API_NOT_FOUND,
                response.getBody().getError());
    }

    @Test
    void shouldHandleMethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex =
                new HttpRequestMethodNotSupportedException("POST",
                        List.of("GET", "PUT"));

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleMethodNotAllowed(ex);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertTrue(response.getBody().getError().contains("GET"));
    }

    @Test
    void shouldHandleUnauthorized() {
        BadCredentialsException ex =
                new BadCredentialsException("Invalid");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleUnauthorized(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldHandleForbidden() {
        AccessDeniedException ex =
                new AccessDeniedException("Denied");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleForbidden(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldHandleIllegalArgument() {
        IllegalArgumentException ex =
                new IllegalArgumentException("Invalid param");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleIllegal(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid param", response.getBody().getError());
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new Exception("Error");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorMessageConstants.INTERNAL_SERVER_ERROR,
                response.getBody().getError());
    }

    @Test
    void shouldHandleTypeMismatch() {

        MethodArgumentTypeMismatchException ex =
                new MethodArgumentTypeMismatchException(
                        "value", Integer.class, "id", null, null);

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getError().contains("id"));
    }

    @Test
    void shouldHandleMissingParam() {

        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("id", "String");

        ResponseEntity<GenericResponse<Object>> response =
                handler.handleMissingParam(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getError().contains("id"));
    }
    
}