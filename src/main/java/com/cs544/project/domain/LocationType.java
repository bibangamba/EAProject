package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    @Embedded
    private AuditData auditData;
}
