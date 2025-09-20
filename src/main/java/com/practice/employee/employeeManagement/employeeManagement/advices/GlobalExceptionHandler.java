package com.practice.employee.employeeManagement.employeeManagement.advices;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ConflictException;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ResourceNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    // If it's a type mismatch (enum conversion), customize the message
                    if (error.contains(TypeMismatchException.class)) {
                        Object rejectedValue = error.getRejectedValue();
                        Class<?> targetType = error.unwrap(TypeMismatchException.class).getRequiredType();
                        if (targetType != null && targetType.isEnum()) {
                            return error.getField() + ": Invalid value '" + rejectedValue +
                                    "'. Allowed values are: " +
                                    Arrays.toString(targetType.getEnumConstants());
                        }
                    }
                    // Default message for normal validation
                    return error.getField() + ": " + error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(subErrors)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<ApiResponse> handleUnknownProperty(UnrecognizedPropertyException ex) {
        String message = String.format("Unknown field '%s' in request. Allowed fields: %s",
                ex.getPropertyName(), ex.getKnownPropertyIds());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(List.of(message))
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
