package com.cs544.project.controller.studentView;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Student;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.AttendanceRecordService;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/student-view/attendance-records")
public class AttendanceRecordsController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    CourseOfferingService courseOfferingService;
    @Autowired
    StudentService studentService;
    @Autowired
    AttendanceRecordService attendanceRecordService;


    @GetMapping()
    public ResponseEntity<?> getCourseOffering() {
        // student should come from the logged-in user
        Student student = studentRepository.findFirstByFirstName("John");
        Collection<AttendanceRecord> allAttendanceRecords = attendanceRecordService.getAllAttendanceRecordsByStudent(student);
        if (allAttendanceRecords.isEmpty()) {
            return new ResponseEntity<String>("No attendance records has been recorded", HttpStatus.OK);
        }
        return new ResponseEntity<Collection<AttendanceRecord>>(allAttendanceRecords, HttpStatus.OK);
    }
}
