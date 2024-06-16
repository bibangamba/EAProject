package com.cs544.project.repository;

import com.cs544.project.domain.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    List<CourseRegistration> findCourseRegistrationByCourseOfferingId(int courseOfferingId);
}
