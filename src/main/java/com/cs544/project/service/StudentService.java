package com.cs544.project.service;

import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student getStudentByStudentID(String id) throws CustomNotFoundException {
        Optional<Student> student = studentRepository.findByStudentID(id);
        return student.orElseThrow(() -> new CustomNotFoundException("Student with id=" + id+ " not found"));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(studentDetails.getFirstName());
                    student.setLastName(studentDetails.getLastName());
                    student.setEmailAddress(studentDetails.getEmailAddress());
                    student.setBirthdate(studentDetails.getBirthdate());
                    student.setGenderType(studentDetails.getGenderType());
                    student.setEntry(studentDetails.getEntry());
                    student.setStudentID(studentDetails.getStudentID());
                    student.setAlternateID(studentDetails.getAlternateID());
                    student.setApplicantID(studentDetails.getApplicantID());
                    student.setFaculty(studentDetails.getFaculty());
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    studentDetails.setId(id);
                    return studentRepository.save(studentDetails);
                });
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentByStudentUserName(String username) {
        return studentRepository.findByUsername(username);
    }
}
