package com.cs544.project.controller.sysadminView;


import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.Faculty;
import com.cs544.project.dto.request.CourseCreateRequest;
import com.cs544.project.repository.CourseRepository;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.CourseService;
import com.cs544.project.service.DatabaseInitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CoursesController.class)
@Import(ReusableBeansTestConfiguration.class)
public class CourseControllerTests {
    @MockBean
    DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CourseRepository courseRepository;

    private Course course;

    @Before
    public void setUp() {
        course = new Course();
        course.setId(1);
        course.setCourseName("testCourseName");
        course.setCourseCode("1234");
        course.setDepartment("TestDepartment");
        course.setCredits(4);
    }

    private CourseCreateRequest getCourseCreateRequest(int credits) {
        CourseCreateRequest createRequest = new CourseCreateRequest();
        createRequest.setCredits(credits);
        createRequest.setCourseCode("12421");
        createRequest.setCourseName("testUpdateCourseName");
        createRequest.setDepartment("TestDepartment");
        return createRequest;
    }

    @Test
    public void testGetAllCourses() throws Exception {
        when(courseService.getCourses()).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/sys-admin/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value(course.getCourseCode()))
                .andExpect(jsonPath("$[0].courseName").value(course.getCourseName()));
    }

    @Test
    public void testCreateCourse() throws Exception {
        when(courseService.addCourse(any(CourseCreateRequest.class))).thenReturn(course);

        mockMvc.perform(post("/sys-admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCourseCreateRequest(4))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value(course.getCourseCode()))
                .andExpect(jsonPath("$.courseName").value(course.getCourseName()));
    }


    @Test
    public void testAddCourseWithCreditsMoreThen8() throws Exception {

        mockMvc.perform(post("/sys-admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(getCourseCreateRequest(400))
                                + "{\"id\": 1}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.credits").value("credits cannot be greater than 8"));
    }

    @Test
    public void testAddCourseWithCreditsLessThen1() throws Exception {

        mockMvc.perform(post("/sys-admin/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(getCourseCreateRequest(-1))
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.credits").value("credits must be at least 1"));
    }


    @Test
    public void testUpdateStudent() throws Exception {
//        when(courseService.updateCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(patch("/sys-admin/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(getCourseCreateRequest(8))
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.credits").value(8));
    }


    @Test
    public void testDeleteStudent() throws Exception {
//        when(courseService.updateCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(delete("/sys-admin/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
