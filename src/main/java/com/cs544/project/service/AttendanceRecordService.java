package com.cs544.project.service;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.AttendanceRecordRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceRecordService {
  private final AttendanceRecordRepository attendanceRecordRepository;

  @Autowired
  public AttendanceRecordService(AttendanceRecordRepository attendanceRecordRepository) {
    this.attendanceRecordRepository = attendanceRecordRepository;
  }

  public List<AttendanceRecord> getAttendanceRecords(
      Student student, LocalDateTime from, LocalDateTime to) throws CustomNotFoundException {
    return attendanceRecordRepository.getAttendanceRecordByStudentAndScanTimeBetween(
        student, from, to);
  }
}
