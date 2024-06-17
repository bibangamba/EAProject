package com.cs544.project.dto.response;

import com.cs544.project.domain.CourseOfferingType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseOfferingDto {
    private int id;
    private int capacity;
    private float credits;
    private String room;
    private LocalDate startDate;
    private LocalDate endDate;
    private CourseOfferingType courseOfferingType;
    private FacultyDto faculty;
    private CourseDto course;
    private AuditDataDto auditData;
}
