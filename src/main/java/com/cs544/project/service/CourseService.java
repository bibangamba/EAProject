package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourseById(int courseId) throws CustomNotFoundException {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomNotFoundException(
                        "Error while creating  a course-offering. Course with id: " + courseId
                                + " was not found in the database."));
    }
}
