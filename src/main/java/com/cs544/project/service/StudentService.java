package com.cs544.project.service;

import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student getStudentByStudentID(String id) throws CustomNotFoundException{
        Optional<Student> student = studentRepository.findByStudentID(id);
        return student.orElseThrow(CustomNotFoundException::new);
    }
}
