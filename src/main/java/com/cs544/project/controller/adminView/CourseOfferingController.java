package com.cs544.project.controller.adminView;

import com.cs544.project.adapter.CourseOfferingAdapter;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.dto.response.CourseOfferingDto;
import com.cs544.project.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RestController("admin-view-offerings")
@RequestMapping("/admin-view/course-offerings")
public class CourseOfferingController {

    @Autowired
    CourseOfferingService courseOfferingService;

    @GetMapping()
    public ResponseEntity<?> getCourseOfferings(@RequestParam("date") String dateYMD) {
        LocalDate queryDate = LocalDate.parse(dateYMD, DateTimeFormatter.ISO_DATE);

        Collection<CourseOffering> offeringsByDate = courseOfferingService.getCourseOfferingsByDate(queryDate);

        List<CourseOfferingDto> courseOfferingsResponse = offeringsByDate.stream()
                .map(CourseOfferingAdapter.INSTANCE::toDto).toList();
        return ResponseEntity.ok(courseOfferingsResponse);
    }
}
