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
public class CourseOfferingTest {

    @Test
    public void testGetAttendedSessions() {
        CourseOffering courseOffering = new CourseOffering();
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        courseOffering.setStartDate(startDate);
        LocalDate endDate = LocalDate.of(2024, 1, 15);
        courseOffering.setEndDate(endDate);
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        LocalDateTime firstMorning = LocalDateTime.of(startDate, LocalTime.of(10, 12));
        LocalDateTime secondMorning = LocalDateTime.of(startDate.plusDays(1), LocalTime.of(10, 0));
        LocalDateTime secondAfternoon = LocalDateTime.of(startDate.plusDays(1), LocalTime.of(13, 40));

        attendanceRecords.add(new AttendanceRecord(firstMorning, new Student(), new Location()));
        attendanceRecords.add(new AttendanceRecord(secondMorning, new Student(), new Location()));
        attendanceRecords.add(new AttendanceRecord(secondAfternoon, new Student(), new Location()));

        List<Session> allSessions = courseOffering.getAttendedSessions(attendanceRecords);

        assertEquals(13, allSessions.size());
        assertTrue(allSessions.get(0).getMorningPeriod().isPresent());
        assertFalse(allSessions.get(0).getAfternoonPeriod().isPresent());
        assertTrue(allSessions.get(1).getMorningPeriod().isPresent());
        assertTrue(allSessions.get(1).getAfternoonPeriod().isPresent());

    }
}