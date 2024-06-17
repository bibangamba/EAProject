package com.cs544.project.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CourseDto {
    private int id;
    private float credits;
    private String courseDescription;
    private String courseCode;
    private String courseName;
    private String department;
    private List<CourseDto> prerequisites;
    private AuditDataDto auditData;
}
