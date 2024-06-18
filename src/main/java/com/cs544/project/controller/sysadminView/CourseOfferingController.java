package com.cs544.project.controller.sysadminView;

import com.cs544.project.adapter.CourseOfferingAdapter;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.dto.request.CreateCourseOfferingRequest;
import com.cs544.project.dto.response.CourseOfferingDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseOfferingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController("sys-admin-offerings")
@RequestMapping("/sys-admin/course-offerings")
@Validated
public class CourseOfferingController {

    private final CourseOfferingService courseOfferingService;

    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @GetMapping("/{offeringId}")
    public ResponseEntity<?> getCourseOfferingById(@PathVariable int offeringId) throws CustomNotFoundException {
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(offeringId);

        return ResponseEntity.ok(CourseOfferingAdapter.INSTANCE.toDto(courseOffering));
    }

    @GetMapping()
    public ResponseEntity<?> getCourseOfferings() {
        Collection<CourseOffering> offeringsByDate = courseOfferingService.getAllCourseOfferings();

        List<CourseOfferingDto> courseOfferingsResponse = offeringsByDate.stream()
                .map(CourseOfferingAdapter.INSTANCE::toDto).toList();
        return ResponseEntity.ok(courseOfferingsResponse);
    }

    @PostMapping()
    public ResponseEntity<?> addCourseOffering(@RequestBody @Valid CreateCourseOfferingRequest createCourseOfferingRequest)
            throws CustomNotFoundException {

        CourseOffering courseOffering = courseOfferingService.createCourseOffering(createCourseOfferingRequest);

        return ResponseEntity.created(URI.create("/sys-admin/course-offerings/" + courseOffering.getId()))
                .body(CourseOfferingAdapter.INSTANCE.toDto(courseOffering));
    }

    @PutMapping("/{offeringId}")
    public ResponseEntity<?> updateCourseOffering(@PathVariable int offeringId,
                                                  @RequestBody @Valid CreateCourseOfferingRequest createCourseOfferingRequest)
            throws CustomNotFoundException {

        CourseOffering courseOffering = courseOfferingService.updateCourseOfferingById(offeringId, createCourseOfferingRequest);

        return ResponseEntity.ok(CourseOfferingAdapter.INSTANCE.toDto(courseOffering));
    }

    @DeleteMapping("/{courseOfferingId}")
    public ResponseEntity<?> deleteCourseOfferingById(@PathVariable int courseOfferingId) {
        courseOfferingService.deleteCourseOffering(courseOfferingId);
        return ResponseEntity.noContent().build();
    }
}
