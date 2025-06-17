package com.veterinary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class HourOnlyValidator implements ConstraintValidator<HourOnly, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.getMinute() == 0 && value.getSecond() == 0 && value.getNano() == 0;
    }
}