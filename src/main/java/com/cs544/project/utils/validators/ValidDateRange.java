package com.cs544.project.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "startDate and endDate validation failed. Date format is yyyy-mm-dd, and endDate must be after startDate.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
