package com.cs544.project.dto.response;

import lombok.Data;

@Data
public class CourseRegistrationDto {
    private int id;
    private double grade;
    private StudentDto student;
    private CourseOfferingDto courseOffering;
}
