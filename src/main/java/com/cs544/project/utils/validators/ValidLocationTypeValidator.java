package com.cs544.project.utils.validators;
import com.cs544.project.dto.request.LocationCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidLocationTypeValidator implements ConstraintValidator<ValidLocationType, LocationCreateRequest> {

    @Override
    public void initialize(ValidLocationType constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(LocationCreateRequest value, ConstraintValidatorContext context) {
        boolean locationTypeIdIsPresent = value.getLocationTypeId() != null;
        boolean locationTypeIsPresent = value.getLocationType() != null;

        // Valid if one or the other is present not both
        return locationTypeIdIsPresent ^ locationTypeIsPresent;
    }
}