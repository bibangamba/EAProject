package com.cs544.project.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Session {
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
}
