package com.veterinary.core.config.exception;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ApiError {
    private int status;
    private String message;
    private String timestamp;

    public ApiError(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.timestamp = timestamp.format(formatter);
    }
}