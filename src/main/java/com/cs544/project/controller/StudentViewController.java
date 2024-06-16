package com.cs544.project.controller;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRegistrationRepository;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    private Student student;

    @Autowired
    CourseOfferingService courseOfferingService;

    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable("offeringId") long offeringId) throws CustomNotFoundException {
        System.out.println(offeringId);
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);
        return ResponseEntity.ok(courseOffering);
    }

    @GetMapping("/course-offerings")
    public ResponseEntity<?> getCourseOffering() {
        student = studentRepository.findFirstByFirstName("John");
        Collection<CourseRegistration> courseRegistrationList = courseRegistrationRepository.findAllByStudent(student);
        Collection<StudentCourse> studentCourses = courseRegistrationList.stream().map(c -> new StudentCourse(student, c)).toList();
        if(studentCourses.isEmpty()) {
            return new ResponseEntity<String>("not found", HttpStatus.OK);
        }
        return new ResponseEntity<Collection<StudentCourse>>(studentCourses, HttpStatus.OK);
    }
}
