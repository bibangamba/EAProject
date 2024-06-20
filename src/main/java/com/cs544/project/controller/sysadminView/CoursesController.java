package com.cs544.project.controller.sysadminView;

import com.cs544.project.domain.Course;
import com.cs544.project.dto.request.CourseCreateRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin/courses")
public class CoursesController {
    @Autowired
    CourseService courseService;

    @GetMapping()
    ResponseEntity<?> getCourses(){
        Collection<Course> courses =  courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    ResponseEntity<?> getCourseById(@PathVariable("courseId") int id) throws CustomNotFoundException {
        Course course =  courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping()
    ResponseEntity<?> addCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest) throws CustomNotFoundException {
        Course savedCourse =  courseService.addCourse(courseCreateRequest);
//        LocationDto savedLocationDto = LocationAdapter.INSTANCE.toDto(savedLocation);
        return ResponseEntity.ok(savedCourse);
    }

    @PatchMapping("/{courseId}")
    ResponseEntity<?> updateCourse(@PathVariable("courseId") int id,@RequestBody Course course){
        Course updatedCourse =  courseService.updateCourse(id,course);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}")
    ResponseEntity<?> deleteCourse(@PathVariable("courseId") int id) throws CustomNotFoundException {
        Boolean status = courseService.deleteCourse(id);
        return ResponseEntity.ok(!status);
    }
}
