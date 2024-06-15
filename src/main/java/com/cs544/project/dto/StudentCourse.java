package com.cs544.project.dto;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import lombok.Data;

@Data
public class StudentCourse {
    private String fullName;
    private double grade;

    private Course course;

    public StudentCourse(Student s, CourseRegistration cs) {
        this.fullName = s.getFirstName() + " " + s.getLastName();
        this.grade = cs.getGrade();
        this.course = cs.getCourseOffering().getCourse();
    }
}
