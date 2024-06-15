package com.cs544.project.repositories;

import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findFirstByFirstName(String firstName);
}
