package com.cs544.project.repository;

import com.cs544.project.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseOfferingRepository  extends JpaRepository<CourseOffering, Integer> {
    Optional<CourseOffering> findById(long id);

    @Query("select co from CourseOffering co where co.startDate <= :queryDate and co.endDate >= :queryDate")
    List<CourseOffering> findCourseOfferingByDate(LocalDate queryDate);
}
