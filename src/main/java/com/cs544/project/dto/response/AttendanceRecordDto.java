package com.cs544.project.dto.response;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDto {
    private int id;
    private LocalDateTime scanTime;
    private StudentDto student;
    private LocationDto location;

    public AttendanceRecordDto(int id, LocalDateTime scanTime, String studentId, String firstName, String lastName, String locationName) {
        this.id = id;
        this.scanTime = scanTime;
        this.student = new StudentDto(studentId, firstName, lastName);
        this.location = new LocationDto(locationName);
    }
}
