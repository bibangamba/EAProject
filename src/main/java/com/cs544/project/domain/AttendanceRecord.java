package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ScanDateTime")
    private LocalDateTime scanTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "StudentId")
    private Student student;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "LocationId")
    private Location location;

    public boolean isMorningAttendance() {
        int year = scanTime.getYear();
        int month = scanTime.getMonthValue();
        int day = scanTime.getDayOfMonth();
        return scanTime.isAfter(LocalDateTime.of(year, month, day, 9, 40))
                && scanTime.isBefore(LocalDateTime.of(year, month, day, 12, 30));
    }
}
