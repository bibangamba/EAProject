package com.cs544.project.utils.validators;

import com.cs544.project.dto.request.CreateCourseOfferingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreateCourseOfferingRequest> {

    private static void updateViolationMessage(ConstraintValidatorContext context, String field, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateCourseOfferingRequest request, ConstraintValidatorContext context) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            updateViolationMessage(context, "startDate & endDate",
                    "startDate and endDate must be provided and cannot be null");
            return false;
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            updateViolationMessage(context, "endDate", "endDate must be after startDate");
            return false;
        }

        return false;
    }
}
