package com.cs544.project.controller.adminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.controller.sysadminView.LocationController;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.DatabaseInitService;
import com.cs544.project.service.LocationService;
import com.cs544.project.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.crypto.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
@Import(ReusableBeansTestConfiguration.class)
public class StudentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    CourseRegistrationService courseRegistrationService;

    @MockBean
    private DatabaseInitService databaseInitService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetStudentsByStudentID_Success() throws Exception {
        Student student = DatabaseInitService.getStudent1(DatabaseInitService.getFaculty1());
        Collection<Course> courses = List.of(DatabaseInitService.getCourse1());

        Mockito.when(studentService.getStudentByStudentID("1")).thenReturn(student);
        when(courseRegistrationService.findAllCourseByStudent(student)).thenReturn(courses);

        mockMvc.perform(get("/admin-view/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(student.getId()))
                .andExpect(jsonPath("registeredCourses").isArray());
    }

    @Test
    public void testGetStudentsByStudentID_NotFound() throws Exception {
        Student student = DatabaseInitService.getStudent1(DatabaseInitService.getFaculty1());
        Collection<Course> courses = List.of(DatabaseInitService.getCourse1());

        Mockito.when(studentService.getStudentByStudentID("1")).thenThrow(new CustomNotFoundException("Student not found"));
        when(courseRegistrationService.findAllCourseByStudent(student)).thenReturn(courses);

        mockMvc.perform(get("/admin-view/students/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ErrorMessage").value("Student not found"));
    }
}
