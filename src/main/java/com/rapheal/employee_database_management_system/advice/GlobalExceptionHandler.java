package com.rapheal.employee_database_management_system.advice;

import com.rapheal.employee_database_management_system.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles MethodArgumentNotValidException thrown when a request body fails @Valid validation.
    // Collects all field errors and returns them in a JSON response along with a timestamp.
    // Responds with HTTP status 400 (Bad Request).

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentValidationExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, Object> body = new HashMap<>();
        Map<String,String> errors = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now().toString());
        var error = ex.getBindingResult().getFieldErrors();
        error.forEach(e-> errors.put(e.getField(),e.getDefaultMessage()));
        body.put("error",errors);

        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    // Handles NoSuchElementException thrown when a requested resource is not found.
    // Returns a JSON response containing the error message and timestamp with HTTP status 404 (Not Found).
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFoundException(ResourceNotFoundException ex){
    Map<String,Object> error = new HashMap<>();
    error.put("error",ex.getMessage());
    error.put("timeStamp", LocalDateTime.now().toString());
        System.out.println("✅ GlobalExceptionHandler is working");
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    // Handles all uncaught exceptions that other does not specifically handle @ExceptionHandler methods.
    // Returns a JSON response containing the error message and timestamp with HTTP status 500 (Internal Server Error).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericException (Exception ex){
        Map<String,Object> body = new HashMap<>();
        body.put("timeStamp",LocalDateTime.now().toString());
        body.put("error",ex.getMessage());

        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
