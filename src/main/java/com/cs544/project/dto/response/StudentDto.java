package com.cs544.project.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class StudentDto extends PersonDto {
    private LocalDate entry;
    private String studentID;
    private String alternateID;
    private String applicantID;
    private FacultyDto faculty;
    private Collection<CourseDto> registeredCourses;

    public StudentDto() {}

    public StudentDto(String studentId, String firstName, String lastName) {
        this.studentID = studentId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
}
