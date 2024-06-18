package com.cs544.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class CourseOffering {
    @Transient
    @JsonIgnore
    Logger logger = LoggerFactory.getLogger(CourseOffering.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int capacity;
    private float credits;
    private String room;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "CourseOfferingType")
    private CourseOfferingType courseOfferingType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "FacultyID")
    private Faculty faculty;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "CourseID")
    private Course course;

    @Embedded
    private AuditData auditData;
    @Transient
    private List<Session> sessions;

    public CourseOffering() {
    }

    public CourseOffering(int credits, int capacity, String room, LocalDate startDate, LocalDate endDate, CourseOfferingType type) {
    }

    public List<Session> getAttendedSessions(List<AttendanceRecord> attendanceRecords) {
        Map<LocalDate, Session> sessionMap = getSessions();
        for (AttendanceRecord record : attendanceRecords) {
            LocalDate scanDate = record.getScanTime().toLocalDate();
            if (sessionMap.get(scanDate) == null) {
                logger.warn(
                        "Found an attendance scan time that could not be mapped to a session date. Record"
                                + record);
                continue;
            }
            if (record.isMorningAttendance()) {
                sessionMap.get(scanDate).getMorningPeriod().setPresent(true);
            } else {
                sessionMap.get(scanDate).getAfternoonPeriod().setPresent(true);
            }
        }
        return sessionMap.values().stream().sorted().toList();
    }

    public Map<LocalDate, Session> getSessions() {
        Map<LocalDate, Session> dateToSessions = new HashMap<>();

        if (this.startDate == null || this.endDate == null) {
            return dateToSessions;
        }

        LocalDate current = this.startDate;
        while (!current.isAfter(this.endDate)) {
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                // skip all sessions on sunday
                current = current.plusDays(1);
                continue;
            }

            Session session = new Session(current);
            if (current.getDayOfWeek() == DayOfWeek.SATURDAY || current.equals(endDate)) {
                // remove afternoon sessions on saturdays and the last day of the course offering
                session.setAfternoonPeriod(null);
            }
            dateToSessions.put(current, session);

            current = current.plusDays(1);
        }
        return dateToSessions;
    }
}
