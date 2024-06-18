package com.cs544.project.service;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.response.AttendanceRecordDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.AttendanceRecordRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

    public ByteArrayInputStream exportAttendanceToExcel(Long courseOfferingId) throws IOException {
        List<AttendanceRecordDto> records = attendanceRecordRepository.findAttendanceRecordsByOfferingId(courseOfferingId);

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Attendance");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Student ID");
            header.createCell(1).setCellValue("Student Name");
            header.createCell(2).setCellValue("Location");
            header.createCell(3).setCellValue("Scan DateTime");

            int rowNum = 1;
            for (AttendanceRecordDto record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getStudent().getStudentID());
                row.createCell(1).setCellValue(record.getStudent().getFirstName() + " " + record.getStudent().getLastName());
                row.createCell(2).setCellValue(record.getLocation().getName());
                row.createCell(3).setCellValue(record.getScanTime().toString());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
