package com.cs544.project.repository;

import com.cs544.project.domain.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
}
