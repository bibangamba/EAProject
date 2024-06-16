package com.cs544.project.controller;

import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-view")
public class AdminViewController {
    @Autowired
    StudentService studentService;

    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getStudentsByStudentID(@PathVariable("studentId") String studentId)
            throws CustomNotFoundException {
        Student student = studentService.getStudentByStudentID(studentId);
        return ResponseEntity.ok(student);
    }
}
