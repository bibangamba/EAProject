package com.cs544.project.service;

import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public Collection<StudentCourse> getCourseByStudent(Student student) {
        Collection<CourseRegistration> courseRegistrationList = courseRegistrationRepository.findAllByStudent(student);
        Collection<StudentCourse> studentCourses = courseRegistrationList.stream().map(c -> new StudentCourse(student, c)).toList();
        return studentCourses;
    }

    public List<CourseRegistration> getCourseRegistrationByCourseOfferingId(int courseOfferingId)
            throws CustomNotFoundException {
        return courseRegistrationRepository.findCourseRegistrationByCourseOfferingId(courseOfferingId);
    }

    public List<Map<String, Object>> getAllCourceOfferingData(int courseOfferingId)
            throws CustomNotFoundException {
        return courseRegistrationRepository.findCourseOfferingWithRegistrations(courseOfferingId);
    }
}
