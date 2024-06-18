package com.cs544.project.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * This class is used to accept request from the client with validation
 * to add Course.
 */
@Data
public class CourseCreateRequest {
    @NotNull(message= "credits cannot be null")
    private int credits;
    @NotNull(message= "courseCode cannot be null")
    private String courseCode;
    @NotNull(message= "courseName cannot be null")
    private String courseName;
    @NotNull(message= "department cannot be null")
    private String department;
}
