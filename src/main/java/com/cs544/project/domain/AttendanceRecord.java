package com.cs544.project.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class AttendanceRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "ScanDateTime")
  private LocalDateTime scanTime;

  @ManyToOne
  @JoinColumn(name = "StudentId")
  private Student student;

  @ManyToOne
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
