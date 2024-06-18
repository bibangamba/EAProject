package com.cs544.project.repository;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.response.AttendanceRecordDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> getAttendanceRecordByStudentAndScanTimeBetween(
            Student student, LocalDateTime start, LocalDateTime end);
    Collection<AttendanceRecord> getByLocation(Location location);

    @Query("SELECT new com.cs544.project.dto.response.AttendanceRecordDto(ar.id, ar.scanTime, s.studentID, s.firstName, s.lastName, l.name)" +
            "FROM AttendanceRecord ar " +
            "JOIN ar.student s " +
            "JOIN CourseRegistration cr ON s.id = cr.student.id " +
            "JOIN cr.courseOffering co " +
            "JOIN ar.location l " +
            "WHERE co.id = :offeringId")
    List<AttendanceRecordDto> findAttendanceRecordsByOfferingId(@Param("offeringId") Long offeringId);

    List<AttendanceRecord> getAttendanceRecordsByStudent(Student student);
}
