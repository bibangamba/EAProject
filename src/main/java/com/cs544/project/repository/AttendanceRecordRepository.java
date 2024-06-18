package com.cs544.project.repository;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> getAttendanceRecordByStudentAndScanTimeBetween(
            Student student, LocalDateTime start, LocalDateTime end);

    @Query("Select a from AttendanceRecord as a")
    List<AttendanceRecord> findByCourseOfferingId(Long courseOfferingId);

}
