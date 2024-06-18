package com.cs544.project.dto.request;

import com.cs544.project.domain.CourseOfferingType;
import com.cs544.project.utils.validators.ValidLocationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CourseOfferingRequest {
    @NotNull(message = "credits is a required attribute")
    private float credits;

    @NotNull(message = "capacity is a required attribute")
    private int capacity;

    @NotBlank(message = "room is a required field")
    private String room;

    @NotNull(message = "startDate is a required field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private LocalDate startDate;

    @NotNull(message = "endDate is a required field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private LocalDate endDate;

    @NotNull(message = "type  can only be one of [PART_TIME, ONLINE, FULL_TIME]")
    @Enumerated(EnumType.STRING)
    private CourseOfferingType type;

    @NotNull(message = "Faculty ID is a required field")
    private int facultyId;

    @NotNull(message = "Course ID is a required field")
    private int courseId;
}
