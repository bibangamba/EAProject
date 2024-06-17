package com.cs544.project.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto extends PersonDto {
    private LocalDate entry;
    private String studentID;
    private String alternateID;
    private String applicantID;
    private FacultyDto faculty;
}
