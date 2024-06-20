package com.cs544.project.repository;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    List<CourseRegistration> findCourseRegistrationByCourseOfferingId(int courseOfferingId);
    public Collection<CourseRegistration> findAllByStudent(Student student);

    public Collection<CourseRegistration> getAllByCourseOffering(CourseOffering courseOffering);
}
