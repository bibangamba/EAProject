package com.cs544.project.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLocationTypeValidator.class)
public @interface ValidLocationType {
    String message() default "Either locationTypeId or locationType must be provided, not both";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}