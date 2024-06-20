package com.cs544.project.service;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.response.AttendanceRecordDto;
import com.cs544.project.dto.response.LocationDto;
import com.cs544.project.dto.response.StudentDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.AttendanceRecordRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceRecordServiceTest {

    @Mock
    private AttendanceRecordRepository attendanceRecordRepository;

    @InjectMocks
    private AttendanceRecordService attendanceRecordService;

    @Test
    public void testGetAttendanceRecords() throws CustomNotFoundException {
        Student student = new Student();
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        List<AttendanceRecord> records = List.of(new AttendanceRecord());

        when(attendanceRecordRepository.getAttendanceRecordByStudentAndScanTimeBetween(student, from, to))
                .thenReturn(records);

        List<AttendanceRecord> foundRecords = attendanceRecordService.getAttendanceRecords(student, from, to);

        assertNotNull(foundRecords, "The returned records list should not be null");
        assertEquals(1, foundRecords.size(), "The size of the returned records list should be 1");
        verify(attendanceRecordRepository, times(1))
                .getAttendanceRecordByStudentAndScanTimeBetween(student, from, to);
    }

    @Test
    public void testGetAttendanceRecords_NoRecordsFound() throws CustomNotFoundException {
        Student student = new Student();
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();

        when(attendanceRecordRepository.getAttendanceRecordByStudentAndScanTimeBetween(student, from, to))
                .thenReturn(Collections.emptyList());

        List<AttendanceRecord> foundRecords = attendanceRecordService.getAttendanceRecords(student, from, to);

        assertNotNull(foundRecords, "The returned records list should not be null");
        assertTrue(foundRecords.isEmpty(), "The returned records list should be empty");
        verify(attendanceRecordRepository, times(1))
                .getAttendanceRecordByStudentAndScanTimeBetween(student, from, to);
    }

    @Test
    public void testExportAttendanceToExcel() throws IOException {
        Long courseOfferingId = 1L;
        AttendanceRecordDto recordDto = new AttendanceRecordDto();
        StudentDto student = new StudentDto();
        student.setStudentID("12345");
        student.setFirstName("John");
        student.setLastName("Doe");
        recordDto.setStudent(student);
        recordDto.setScanTime(LocalDateTime.now());
        recordDto.setLocation(new LocationDto("Test Location"));
        List<AttendanceRecordDto> records = List.of(recordDto);

        when(attendanceRecordRepository.findAttendanceRecordsByOfferingId(courseOfferingId)).thenReturn(records);

        ByteArrayInputStream byteArrayInputStream = attendanceRecordService.exportAttendanceToExcel(courseOfferingId);

        assertNotNull(byteArrayInputStream, "The returned ByteArrayInputStream should not be null");

        try (Workbook workbook = new XSSFWorkbook(byteArrayInputStream)) {
            assertEquals(1, workbook.getNumberOfSheets(), "The workbook should contain one sheet");
            assertEquals("Attendance", workbook.getSheetAt(0).getSheetName(), "The sheet name should be 'Attendance'");
        }

        verify(attendanceRecordRepository, times(1)).findAttendanceRecordsByOfferingId(courseOfferingId);
    }

    @Test
    public void testGetAllAttendanceRecordsByStudent() {
        Student student = new Student();
        List<AttendanceRecord> records = List.of(new AttendanceRecord());

        when(attendanceRecordRepository.getAttendanceRecordsByStudent(student)).thenReturn(records);

        List<AttendanceRecord> foundRecords = attendanceRecordService.getAllAttendanceRecordsByStudent(student);

        assertNotNull(foundRecords, "The returned records list should not be null");
        assertEquals(1, foundRecords.size(), "The size of the returned records list should be 1");
        verify(attendanceRecordRepository, times(1)).getAttendanceRecordsByStudent(student);
    }

    @Test
    public void testGetAllAttendanceRecordsByStudent_NoRecordsFound() {
        Student student = new Student();

        when(attendanceRecordRepository.getAttendanceRecordsByStudent(student))
                .thenReturn(Collections.emptyList());

        List<AttendanceRecord> foundRecords = attendanceRecordService.getAllAttendanceRecordsByStudent(student);

        assertNotNull(foundRecords, "The returned records list should not be null");
        assertTrue(foundRecords.isEmpty(), "The returned records list should be empty");
        verify(attendanceRecordRepository, times(1)).getAttendanceRecordsByStudent(student);
    }
}
