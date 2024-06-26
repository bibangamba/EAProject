package com.cs544.project.domain;

import com.cs544.project.service.DatabaseInitService;
import jakarta.persistence.*;
import lombok.Data;
import org.mapstruct.MapperConfig;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Capacity")
    private Integer capacity;

    @Column(name = "Name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    private LocationType locationType;

    @Embedded
    private AuditData auditData;

    public static Location getSampleData(){
        return DatabaseInitService.getLocation1(
                DatabaseInitService.getLectureHall()
        );
    }
}
