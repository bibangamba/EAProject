package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Student extends Person {
    @Column(name = "Entry")
    private LocalDate entry;

    @Column(name = "StudentID")
    private String studentID;

    @Column(name = "AlternateID")
    private String alternateID;

    @Column(name = "ApplicantID")
    private String applicantID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FacultyAdviserID")
    private Faculty faculty;

    @OneToMany(mappedBy = "student")
    private List<CourseRegistration> courseRegistrationList;
}
