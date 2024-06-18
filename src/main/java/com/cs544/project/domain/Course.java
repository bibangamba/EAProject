package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float credits;

    @Column(name = "CourseDescription")
    private String courseDescription;

    @Column(name = "CourseCode")
    private String courseCode;

    @Column(name = "CourseName")
    private String courseName;

    private String department;

    @OneToMany
    @JoinTable(
            name = "CoursePrerequisite",
            joinColumns = @JoinColumn(name = "CourseId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "PrerequisiteId", referencedColumnName = "id"))
    private List<Course> prerequisites;

    @Embedded
    private AuditData auditData;

    public Course() {
    }

    public Course(float credits, String courseDescription, String courseCode, String courseName, String department) {
        this.credits = credits;
        this.courseDescription = courseDescription;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
    }

}
