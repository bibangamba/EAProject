package com.cs544.project.controllers.studentView;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.repositories.CourseRegistrationRepository;
import com.cs544.project.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class CourseOfferingController{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    private Student student;

    @GetMapping("/student-view/course-offerings")
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
