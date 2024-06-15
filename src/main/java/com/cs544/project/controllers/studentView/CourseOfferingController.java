package com.cs544.project.controllers.studentView;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseOfferingController{
    @Autowired
    private StudentRepository studentRepository;
    private Student student;

    @GetMapping("/student-view/course-offerings")
    public ResponseEntity<?> getCourseOffering() {
        student = studentRepository.findFirstByFirstName("John");

        List<CourseRegistration> courseRegistrationList = student.getCourseRegistrationList();
        if(courseRegistrationList.isEmpty()) {
            return new ResponseEntity<String>("not found", HttpStatus.OK);
        }
        return new ResponseEntity<List<CourseRegistration>>(courseRegistrationList, HttpStatus.OK);
    }
}
