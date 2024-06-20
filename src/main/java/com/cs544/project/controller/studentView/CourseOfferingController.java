package com.cs544.project.controller.studentView;

import com.cs544.project.adapter.CourseOfferingAdapter;
import com.cs544.project.controller.ApplicationController;
import com.cs544.project.domain.*;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.cs544.project.service.AttendanceRecordService;
import com.cs544.project.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController("student-view")
@RequestMapping("/student-view/course-offerings")
public class CourseOfferingController extends ApplicationController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRegistrationService courseRegistrationService;

    @Autowired
    CourseOfferingService courseOfferingService;
    @Autowired
    StudentService studentService;
    @Autowired
    AttendanceRecordService attendanceRecordService;

    @GetMapping("/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable("offeringId") int offeringId)
            throws CustomNotFoundException {
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);
        return ResponseEntity.ok(CourseOfferingAdapter.INSTANCE.toDto(courseOffering));
    }

    @GetMapping()
    public ResponseEntity<?> getCourseOffering() throws CustomNotFoundException {
        Person currentPerson = getCurrentPerson();
//        Student student = studentRepository.findFirstByFirstName(currentPerson.getUsername());
        Student student = studentService.getStudentByStudentUserName(getCurrentPerson().getUsername());
        System.out.println("###### "+student);
        System.out.println("###### "+getCurrentPerson().getUsername());

        Collection<StudentCourse> studentCourses = courseRegistrationService.getCourseByStudent(student);
        if (studentCourses.isEmpty()) {
            return new ResponseEntity<String>("not found", HttpStatus.OK);
        }
        return new ResponseEntity<Collection<StudentCourse>>(studentCourses, HttpStatus.OK);
    }
    @GetMapping("/{offeringId}/attendance")
    public ResponseEntity<?> getCourseOfferingAttendance(@PathVariable("offeringId") int offeringId)
            throws CustomNotFoundException {
        Student student = studentService.getStudentByStudentUserName(getCurrentPerson().getUsername());

        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);

        LocalDateTime start = courseOffering.getStartDate().atStartOfDay();
        LocalDateTime end = courseOffering.getEndDate().plusDays(1).atStartOfDay();

        List<AttendanceRecord> attendanceRecords =
                attendanceRecordService.getAttendanceRecords(student, start, end);
        List<Session> sessions = courseOffering.getAttendedSessions(attendanceRecords);

        return ResponseEntity.ok(sessions);
    }
}
