package com.veterinary.core.config.exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private LocalDateTime timeStamp;
    private String requestURL;

    private ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String requestURL) {
        this();
        this.status = status;
        this.message = message;
        this.requestURL = requestURL;
    }
}