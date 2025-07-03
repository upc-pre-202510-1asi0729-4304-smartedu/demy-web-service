package com.smartedu.demy.platform.shared.infrastructure.web;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for the entire application.
 * <p>
 * Captures specific and generic exceptions thrown by controllers,
 * logs them, and returns a structured JSON error response with
 * an appropriate HTTP status code.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles {@link RuntimeException} and returns a 400 Bad Request response.
     *
     * @param ex      the RuntimeException thrown
     * @param request the current web request
     * @return a structured error response with HTTP 400
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        LOGGER.error("Runtime exception occurred: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalArgumentException} and returns a 400 Bad Request response.
     *
     * @param ex      the IllegalArgumentException thrown
     * @param request the current web request
     * @return a structured error response with HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        LOGGER.error("Illegal argument exception occurred: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic {@link Exception} and returns a 500 Internal Server Error response.
     *
     * @param ex      the Exception thrown
     * @param request the current web request
     * @return a structured error response with HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {

        LOGGER.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds a structured error response as a map with standard fields.
     *
     * @param status  the HTTP status code
     * @param error   the error type
     * @param message the detailed error message
     * @param path    the request path
     * @return a map representing the error response
     */
    private Map<String, Object> createErrorResponse(int status, String error, String message, String path) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("path", path);
        return errorResponse;
    }
}