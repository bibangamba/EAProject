package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CourseOffering {
    @Id
    @GeneratedValue
    private int id;

    private int capacity;
    private float credits;
    @Enumerated(EnumType.STRING)
    private CourseOfferingType courseOfferingType;
    private String room;

    @ManyToOne
    @JoinColumn(name = "FacultyID")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "CourseID")
    private Course course;

    @Embedded
    private AuditData auditData;

    @Transient
    private List<Session> session;
}
