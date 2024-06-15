package com.cs544.project.repository;

import com.cs544.project.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseOfferingRepository  extends JpaRepository<CourseOffering, Integer> {
}
