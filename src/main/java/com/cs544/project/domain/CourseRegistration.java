package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double grade;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "CourseOfferingId")
    private CourseOffering courseOffering;

//    TODO: The database requirements has a "Sequence" column in table "CourseRegistration".
//    @OrderColumn(name="Sequence")
//    @Column(name="Sequence")
//    int sequence;
}
