package com.cs544.project.repository;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
  List<AttendanceRecord> getAttendanceRecordByStudentAndScanTimeBetween(
      Student student, LocalDateTime start, LocalDateTime end);
}
