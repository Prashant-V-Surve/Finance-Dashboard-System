package com.zorvyn.finance_dashboard_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    //Handle ResourceNotFound
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFoundException (ResourceNotFoundException ex) {

        Map<String,Object> error = new HashMap<>();
        error.put("timeStamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException (Exception ex) {
        Map<String,Object> error = new HashMap<>();
        error.put("timeStamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", 500);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
