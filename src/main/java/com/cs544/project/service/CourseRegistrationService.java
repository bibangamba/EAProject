package com.cs544.project.service;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import com.cs544.project.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseOfferingRepository courseOfferingRepository;

    @Autowired
    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository, CourseOfferingRepository courseOfferingRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.courseOfferingRepository = courseOfferingRepository;
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

    public Collection<CourseRegistration> getAllCourseOfferingData(int courseOfferingId)
            throws CustomNotFoundException {
        Optional<CourseOffering> courseOffering = courseOfferingRepository.findById(courseOfferingId);
        Collection<CourseRegistration> courseRegistrations = courseRegistrationRepository.getAllByCourseOffering(courseOffering.get());
        return courseRegistrations;
    }
}
