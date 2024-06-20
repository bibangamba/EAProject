package com.cs544.project.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class AttendanceRecordTest {

    @Test
    public void testIsMorningAttendance() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        LocalDateTime firstMorning = LocalDateTime.of(startDate, LocalTime.of(10, 12));
        LocalDateTime secondMorning = LocalDateTime.of(startDate.plusDays(1), LocalTime.of(10, 0));
        LocalDateTime secondAfternoon = LocalDateTime.of(startDate.plusDays(1), LocalTime.of(13, 40));

        attendanceRecords.add(new AttendanceRecord(firstMorning, new Student(), new Location()));
        attendanceRecords.add(new AttendanceRecord(secondMorning, new Student(), new Location()));
        attendanceRecords.add(new AttendanceRecord(secondAfternoon, new Student(), new Location()));

        assertTrue(attendanceRecords.getFirst().isMorningAttendance());
        assertTrue(attendanceRecords.get(1).isMorningAttendance());
        assertFalse(attendanceRecords.get(2).isMorningAttendance());

    }
}