package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.dto.request.CourseOfferingRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static com.cs544.project.domain.CourseOffering.getCourseOfferingFromRequest;

@Service
public class CourseOfferingService {
    private final CourseOfferingRepository courseOfferingRepository;
    private final CourseService courseService;
    private final FacultyService facultyService;

    @Autowired
    public CourseOfferingService(CourseOfferingRepository courseOfferingRepository, CourseService courseService, FacultyService facultyService) {
        this.courseOfferingRepository = courseOfferingRepository;
        this.courseService = courseService;
        this.facultyService = facultyService;
    }

    public CourseOffering getCourseOfferingById(int id) throws CustomNotFoundException {
        Optional<CourseOffering> courseOffering = courseOfferingRepository.findById(id);
        if (courseOffering.isPresent()) {
            return courseOffering.get();
        }
        throw new CustomNotFoundException("The course with id=" + id + " not found");
    }

    public Collection<CourseOffering> getCourseOfferingsByDate(LocalDate queryDate) {
        return courseOfferingRepository.findCourseOfferingByDate(queryDate);
    }

    public Collection<CourseOffering> getAllCourseOfferings() {
        return courseOfferingRepository.findAll();
    }

    public void deleteCourseOffering(int courseOfferingId) {
        courseOfferingRepository.deleteById(courseOfferingId);
    }

    public CourseOffering createCourseOffering(@Valid CourseOfferingRequest courseOfferingRequest) throws CustomNotFoundException {
        Course course = courseService.getCourseById(courseOfferingRequest.getCourseId());
        Faculty faculty = facultyService.getFacultyById(courseOfferingRequest.getFacultyId());
        CourseOffering courseOffering = getCourseOfferingFromRequest(new CourseOffering(), courseOfferingRequest, course, faculty);

        return courseOfferingRepository.save(courseOffering);
    }


    public CourseOffering updateCourseOfferingById(int offeringId, CourseOfferingRequest courseOfferingRequest) throws CustomNotFoundException {
        Course course = courseService.getCourseById(courseOfferingRequest.getCourseId());
        Faculty faculty = facultyService.getFacultyById(courseOfferingRequest.getFacultyId());
        CourseOffering courseOffering = this.getCourseOfferingById(offeringId);

        getCourseOfferingFromRequest(courseOffering, courseOfferingRequest, course, faculty);

        return courseOfferingRepository.save(courseOffering);

    }
}
