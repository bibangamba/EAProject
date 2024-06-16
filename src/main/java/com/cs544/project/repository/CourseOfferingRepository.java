package com.cs544.project.repository;

import com.cs544.project.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseOfferingRepository  extends JpaRepository<CourseOffering, Integer> {
    Optional<CourseOffering> findById(long id);
}
