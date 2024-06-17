package com.cs544.project.service;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class CourseOfferingService {
    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    public CourseOffering getCourseOfferingById(int id) throws CustomNotFoundException {
        Optional<CourseOffering> courseOffering = courseOfferingRepository.findById(id);
        if (courseOffering.isPresent()) {
            return courseOffering.get();
        }
        throw new CustomNotFoundException("The course with id:" + id + " not found");
    }

    public Collection<CourseOffering> getCourseOfferingsByDate(LocalDate queryDate) {
        return courseOfferingRepository.findCourseOfferingByDate(queryDate);
    }
}
