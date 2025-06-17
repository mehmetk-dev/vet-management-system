package com.veterinary.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HourOnlyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HourOnly {
    String message() default "Randevu sadece saat başlarında alınabilir (örneğin 13:00, 14:00 gibi)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}