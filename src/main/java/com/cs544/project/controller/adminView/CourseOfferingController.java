package com.cs544.project.controller.adminView;

import com.cs544.project.adapter.CourseOfferingAdapter;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.dto.response.CourseOfferingDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController("admin-view-offerings")
@RequestMapping("/admin-view/course-offerings")
public class CourseOfferingController {

    @Autowired
    CourseOfferingService courseOfferingService;

    @Autowired
    private CourseRegistrationService courseRegistrationService;

    @GetMapping()
    public ResponseEntity<?> getCourseOfferings(@RequestParam("date") String dateYMD) {
        LocalDate queryDate;
        try{
            queryDate = LocalDate.parse(dateYMD, DateTimeFormatter.ISO_DATE);
        }catch (DateTimeParseException e){
            return ResponseEntity.badRequest().body("parsingError: 'date' request parameter must be in the yyyy-mm-dd format. Received: "+dateYMD);
        }

        Collection<CourseOffering> offeringsByDate = courseOfferingService.getCourseOfferingsByDate(queryDate);

        List<CourseOfferingDto> courseOfferingsResponse = offeringsByDate.stream()
                .map(CourseOfferingAdapter.INSTANCE::toDto).toList();
        return ResponseEntity.ok(courseOfferingsResponse);
    }

    @GetMapping("/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable("offeringId") int offeringId)
            throws CustomNotFoundException {
        Collection<CourseRegistration>  courseOfferingData = courseRegistrationService.getAllCourseOfferingData(offeringId);
        return ResponseEntity.ok(courseOfferingData);
    }
}
