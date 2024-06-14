package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "CourseOfferingId")
    private CourseOffering courseOffering;
}
