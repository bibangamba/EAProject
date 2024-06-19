package com.cs544.project.dto.request;

import com.cs544.project.utils.validators.ValidDateRange;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
/**
 * This class is used to accept request from the client with validation
 * to add Course.
 */
@Data
public class CourseCreateRequest {

    @NotNull(message= "credits cannot be null")
    @Min(value = 1, message = "credits must be at least 1")
    @Max(value = 8, message = "credits cannot be greater than 8")
    private int credits;
    @NotNull(message= "courseCode cannot be null")
    private String courseCode;
    @NotNull(message= "courseName cannot be null")
    private String courseName;
    @NotNull(message= "department cannot be null")
    private String department;
}
