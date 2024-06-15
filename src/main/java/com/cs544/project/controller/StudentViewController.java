package com.cs544.project.controller;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    CourseOfferingService courseOfferingService;

    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable("offeringId") long offeringId) throws CustomNotFoundException {
        System.out.println(offeringId);
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);
        return ResponseEntity.ok(courseOffering);
    }
}
