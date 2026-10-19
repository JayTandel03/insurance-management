package com.onerivet.exception;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GenericResponse<T>> handleBadRequest(BadRequestException ex) {
        logger.error("BadRequestException: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<T>> handleValidation(
            MethodArgumentNotValidException ex) {

        String message =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .distinct()
                        .collect(Collectors.joining(", "));

        if (message.isEmpty()) {

            message = ErrorMessageConstants.VALIDATION_FAILED;
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse<>(message));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse<T>> handleNotFound(ResourceNotFoundException ex) {
        logger.error("ResourceNotFoundException: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GenericResponse<T>> handleNoHandler(NoHandlerFoundException ex) {
        logger.error("NoHandlerFoundException: {}", ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericResponse<>(ErrorMessageConstants.API_NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GenericResponse<T>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {

        String message = ErrorMessageConstants.METHOD_NOT_ALLOWED + String.join(", ", ex.getSupportedMethods());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new GenericResponse<>(message));
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<GenericResponse<T>> handleUnauthorized(Exception ex) {
        logger.error("Unauthorized: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GenericResponse<>(ErrorMessageConstants.UNAUTHORIZED_MESSAGE));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse<T>> handleForbidden(AccessDeniedException ex) {
        logger.error("AccessDenied: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new GenericResponse<>(ErrorMessageConstants.FORBIDDEN_MESSAGE));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<T>> handleIllegal(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<T>> handleException(Exception ex) {
        logger.error("Internal Server Error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse<>(ErrorMessageConstants.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericResponse<T>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("TypeMismatch: {}", ex.getMessage());

        String requiredType = Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName)
                .orElse(Constants.UNKNOWN);

        String message = ErrorMessageConstants.PARAMETER + ex.getName() + ErrorMessageConstants.INVALID_TYPE
                + requiredType;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>(message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericResponse<T>> handleMissingParam(MissingServletRequestParameterException ex) {

        String message = ErrorMessageConstants.INVALID_PARAMETER + ex.getParameterName();

        return new ResponseEntity<>(new GenericResponse<>(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<T>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        logger.error(
                "Invalid request body: {}",
                ex.getMessage());

        String message =
                ErrorMessageConstants.INVALID_REQUEST_BODY;

        String exceptionMessage =
                ex.getMessage();

        if (exceptionMessage != null
                && (exceptionMessage.contains(Constants.BIG_DECIMAL)
                || exceptionMessage.contains(Constants.INTEGER)
                || exceptionMessage.contains(Constants.LONG)
                || exceptionMessage.contains(Constants.DOUBLE))) {

            message =
                    ErrorMessageConstants.INVALID_NUMERIC_VALUE;
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse<>(message));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<GenericResponse<T>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        logger.error("ResourceAlreadyExistsException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new GenericResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(PaymentValidationException.class)
    public ResponseEntity<GenericResponse<T>> handlePaymentValidationException(PaymentValidationException ex) {

        return new ResponseEntity<>(new GenericResponse<>(ex.getMessage()), HttpStatus.CONFLICT);
    }

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<GenericResponse<T>> handleMissingPathVariable(MissingPathVariableException ex) {

		return ResponseEntity.badRequest().body(new GenericResponse<>(ex.getVariableName() + ErrorMessageConstants.IS_REQUIRED));
	}
}