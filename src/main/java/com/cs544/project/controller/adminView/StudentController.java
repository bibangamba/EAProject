package com.cs544.project.controller.adminView;

import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("SysAdmin")
@RequestMapping("/admin-view/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentsByStudentID(@PathVariable("studentId") String studentId)
            throws CustomNotFoundException {
        Student student = studentService.getStudentByStudentID(studentId);
        return ResponseEntity.ok(student);
    }
}
