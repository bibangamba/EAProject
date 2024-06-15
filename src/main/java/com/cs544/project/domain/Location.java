package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name = "Capacity")
    private int capacity;
    @Column(name="Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private LocationType locationType;

    @Embedded
    private AuditData auditData;
}
