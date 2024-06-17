package com.cs544.project.advice;

import com.cs544.project.exception.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * This Advice is an AOP concept which handles the corresponding Exception
 * and return a Map including an error Message
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // Handles any exception of type CustomNotFoundException
    // These exception are handled in the Controller Level due to @RestControllerAdvice Annotation
    @ExceptionHandler(CustomNotFoundException.class)

    public ResponseEntity<?> handleCustomNotFoundException(CustomNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }
}
