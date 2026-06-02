package com.sangng.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sangng.restaurant.respone.ApiRespone;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiRespone> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiRespone(ex.getMessage(), null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespone> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiRespone("An unexpected error occurred: " + ex.getMessage(), null));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiRespone> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiRespone(ex.getMessage(), null));
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiRespone> handleJwtException(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiRespone(ex.getMessage(), null));
    }
}
