package com.cs544.project.repository;

import com.cs544.project.domain.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  Optional<Student> findByStudentID(String id);
}
