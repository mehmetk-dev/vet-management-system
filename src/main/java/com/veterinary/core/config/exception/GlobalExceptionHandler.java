package com.veterinary.core.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex, WebRequest webRequest) {
        ApiError apiError = new ApiError
                (HttpStatus.CONFLICT, ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest webRequest){
        ApiError apiError = new ApiError
                (HttpStatus.NOT_FOUND, e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException e, WebRequest request){
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).toList();
        ApiError apiError = new ApiError
                (HttpStatus.BAD_REQUEST, String.join(", ", validationErrorList),request.getDescription(false));
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
    }

