package com.cs544.project.advice;

import com.cs544.project.exception.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * This Advice is an AOP concept which handles the corresponding Exception
 * and return a Map including an error Message
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    // Handles any exception of type CustomNotFoundException
    // These exception are handled in the Controller Level due to @RestControllerAdvice Annotation
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(CustomNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("ErrorMessage", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            String errorMessage = error.getDefaultMessage();
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
            } else {
                fieldName = error.getObjectName(); // or handle differently if necessary
            }
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleParsingExceptions(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ParsingError", ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParametersExceptions(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("MissingParameter", ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMissingParametersExceptions(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("IncorrectParameterType", ex.getLocalizedMessage()));
    }
}
