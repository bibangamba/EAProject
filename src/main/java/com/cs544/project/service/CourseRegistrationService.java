package com.cs544.project.service;

import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<CourseRegistration> getCourseRegistrationByCourseOfferingId(int courseOfferingId)
            throws CustomNotFoundException {
        return courseRegistrationRepository.findCourseRegistrationByCourseOfferingId(courseOfferingId);
    }
}
