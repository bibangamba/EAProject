package com.cs544.project.controller.adminView;

import com.cs544.project.adapter.StudentAdapter;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.response.StudentDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRegistrationRepository;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController("admin-view-students")
@RequestMapping("/admin-view/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseRegistrationService courseRegistrationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentsByStudentID(@PathVariable("studentId") String studentId)
            throws CustomNotFoundException {
        Student student = studentService.getStudentByStudentID(studentId);
        Collection<Course> courses = courseRegistrationService.findAllCourseByStudent(student);
        StudentDto studentDto = StudentAdapter.INSTANCE.toDto(student, courses);
        return ResponseEntity.ok(studentDto);
    }
}
