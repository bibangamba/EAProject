package com.cs544.project.controller.studentView;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.cs544.project.domain.Session;
import com.cs544.project.service.AttendanceRecordService;
import com.cs544.project.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController("student-view")
@RequestMapping("/student-view/course-offerings")
public class CourseOfferingController {
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
        return ResponseEntity.ok(courseOffering);
    }

    @GetMapping()
    public ResponseEntity<?> getCourseOffering() {
        // student should came from the logged in user
        Student student = studentRepository.findFirstByFirstName("John");
        Collection<StudentCourse> studentCourses = courseRegistrationService.getCourseByStudent(student);
        if (studentCourses.isEmpty()) {
            return new ResponseEntity<String>("not found", HttpStatus.OK);
        }
        return new ResponseEntity<Collection<StudentCourse>>(studentCourses, HttpStatus.OK);
    }
    @GetMapping("/{offeringId}/attendance")
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
