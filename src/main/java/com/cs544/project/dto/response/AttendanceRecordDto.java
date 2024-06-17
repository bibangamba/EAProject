package com.cs544.project.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDto {
    private int id;
    private LocalDateTime scanTime;
    private StudentDto student;
    private LocationDto location;
}
