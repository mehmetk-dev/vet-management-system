package com.veterinary.core.config.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final int statusCode;

    public EntityAlreadyExistsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
