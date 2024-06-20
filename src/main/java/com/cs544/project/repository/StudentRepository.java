package com.cs544.project.repository;

import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findFirstByFirstName(String firstName);
    public Optional<Student> findByStudentID(String id);

    public Student findByUsername(String username);
}
