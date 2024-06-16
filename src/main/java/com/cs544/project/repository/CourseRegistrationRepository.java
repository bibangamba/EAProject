package com.cs544.project.repository;

import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    List<CourseRegistration> findCourseRegistrationByCourseOfferingId(int courseOfferingId);
    public Collection<CourseRegistration> findAllByStudent(Student student);

}
