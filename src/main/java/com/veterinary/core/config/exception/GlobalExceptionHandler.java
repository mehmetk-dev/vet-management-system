package com.veterinary.core.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex, WebRequest webRequest) {
        ApiError apiError = new ApiError
                (HttpStatus.CONFLICT, ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleEntityAlreadyExists(BadRequestException ex, WebRequest webRequest) {
        ApiError apiError = new ApiError
                (HttpStatus.BAD_REQUEST, ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    //Yanlış endpoint girilmesini handler eden metot
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException ex,WebRequest webRequest) {
        ApiError apiError =  new ApiError(
                HttpStatus.NOT_FOUND,
                "Endpoint bulunamadı: " + ex.getRequestURL(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    //Yanlış HTTP metodunu handle eden metot
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleNotFound(HttpRequestMethodNotSupportedException ex,WebRequest webRequest) {
        ApiError apiError =  new ApiError(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Bu endpoint için bu HTTP methoduna izin verilmiyor: " + ex.getMethod(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }
}

