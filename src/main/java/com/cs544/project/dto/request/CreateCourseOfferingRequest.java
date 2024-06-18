package com.cs544.project.dto.request;

import com.cs544.project.domain.CourseOfferingType;
import com.cs544.project.utils.validators.ValidDateRange;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ValidDateRange
public class CreateCourseOfferingRequest {
    @NotNull(message = "credits is a required attribute")
    @Min(value = 1, message = "credits must be at least 1")
    @Max(value = 8, message = "credits cannot be greater than 8")
    private float credits;

    @NotNull(message = "capacity is a required attribute")
    @Min(value = 1, message = "capacity must be at least 1")
    private int capacity;

    @NotBlank(message = "room is a required field")
    private String room;

    @NotNull(message = "startDate is a required field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "endDate is a required field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "type  can only be one of [PART_TIME, ONLINE, FULL_TIME]")
    @Enumerated(EnumType.STRING)
    private CourseOfferingType type;

    @NotNull(message = "Faculty ID is a required field")
    @Min(value = 1, message = "facultyId cannot be zero or empty")
    private int facultyId;

    @NotNull(message = "Course ID is a required field")
    @Min(value = 1, message = "courseId cannot be zero or empty")
    private int courseId;
}
