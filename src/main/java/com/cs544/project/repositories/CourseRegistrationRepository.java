package com.cs544.project.repositories;

import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    public Collection<CourseRegistration> findAllByStudent(Student student);
}
