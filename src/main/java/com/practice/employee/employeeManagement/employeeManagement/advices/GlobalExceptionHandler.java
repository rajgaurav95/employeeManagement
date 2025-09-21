package com.practice.employee.employeeManagement.employeeManagement.advices;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ConflictException;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ResourceNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse> handleConflictErrors(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    // Handle enum conversion errors
                    if (error.contains(TypeMismatchException.class)) {
                        Object rejectedValue = error.getRejectedValue();
                        Class<?> targetType = error.unwrap(TypeMismatchException.class).getRequiredType();
                        if (targetType != null && targetType.isEnum()) {
                            return error.getField() + ": Invalid value '" + rejectedValue +
                                    "'. Allowed values are: " + Arrays.toString(targetType.getEnumConstants());
                        }
                    }
                    return error.getField() + ": " + error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(subErrors)
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        String message = "Database error";
        if (rootMessage != null && rootMessage.contains("duplicate key value")) {
            message = "Duplicate entry detected: " + extractConstraintMessage(rootMessage);
        }

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(message)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    private String extractConstraintMessage(String dbMessage) {
        // Example dbMessage:
        // ERROR: duplicate key value violates unique constraint "employee_email_key"
        // Detail: Key (email)=(gk@gmail.com) already exists.
        int detailIndex = dbMessage.indexOf("Detail:");
        if (detailIndex != -1) {
            return dbMessage.substring(detailIndex).replace("Detail:", "").trim();
        }
        return dbMessage;
    }


    // Catch Jackson wrapper for unknown fields
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException upe = (UnrecognizedPropertyException) cause;
            String message = String.format("Unknown field '%s' in request", upe.getPropertyName());

            ApiError apiError = ApiError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Input validation failed")
                    .subErrors(List.of(message))
                    .build();
            return buildErrorResponseEntity(apiError);
        }

        // fallback for other parse errors
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Malformed JSON request")
                .subErrors(List.of(ex.getMessage()))
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse(apiError), apiError.getStatus());
    }
}