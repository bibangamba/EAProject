package com.cs544.project.controller;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Session;
import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.AttendanceRecordService;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    CourseOfferingService courseOfferingService;
    @Autowired
    CourseRegistrationService courseRegistrationService;
    @Autowired
    StudentService studentService;
    @Autowired
    AttendanceRecordService attendanceRecordService;

    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable("offeringId") int offeringId)
            throws CustomNotFoundException {
        System.out.println(offeringId);
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);
        return ResponseEntity.ok(courseOffering);
    }

    @GetMapping("/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getCourseOfferingAttendance(@PathVariable("offeringId") int offeringId)
            throws CustomNotFoundException {
        // TODO: to be replaced by session-based student (student currently logged in)
        Student student = studentService.getAllStudents().getFirst();

        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);

        LocalDateTime start = courseOffering.getStartDate().atStartOfDay();
        LocalDateTime end = courseOffering.getEndDate().plusDays(1).atStartOfDay();

        List<AttendanceRecord> attendanceRecords =
                attendanceRecordService.getAttendanceRecords(student, start, end);
        List<Session> sessions = courseOffering.getAttendedSessions(attendanceRecords);

        return ResponseEntity.ok(sessions);
    }
}
