package com.cs544.project.controllers.studentView;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.repositories.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseOfferingController{
    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    @GetMapping("/student-view/course-offerings")
    public ResponseEntity<?> getCourseOffering() {
        List<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        System.out.println("-------------------output------");
        System.out.println(courseOfferings);
        return new ResponseEntity<List<CourseOffering>>(courseOfferings, HttpStatus.OK);
    }
}
