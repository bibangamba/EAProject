package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.dto.request.CourseCreateRequest;

import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


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

    public Collection<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course addCourse(CourseCreateRequest courseCreateRequest) throws CustomNotFoundException {
        Course course = new Course();
        course.setCourseCode(courseCreateRequest.getCourseCode());
        course.setCourseName(courseCreateRequest.getCourseName());
        course.setCredits(courseCreateRequest.getCredits());
        course.setDepartment(courseCreateRequest.getDepartment());
        // TODO: Set Audit Data
        System.out.println(course);
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, Course course){
        if(!courseRepository.existsById(id)){
            course.setId(id);
            return courseRepository.save(course);
        }
        course.setId(id);
        return courseRepository.save(course);
    }

    public boolean deleteCourse(int id) throws CustomNotFoundException {
        courseRepository.deleteById(id);
        return courseRepository.existsById(id);

    }
}
