package com.cs544.project.controller.studentView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.PersonAccountRepository;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@WebMvcTest(CourseOfferingController.class)
@Import(ReusableBeansTestConfiguration.class)
@WithMockUser(username = "bibangamba", roles = "FACULTY")
public class CourseOfferingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version

    @MockBean
    private CourseRegistrationService courseRegistrationService;

    @MockBean
    private CourseOfferingService courseOfferingService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private PersonAccountRepository personAccountRepository;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @MockBean
    private StudentRepository studentRepository;
    @Before
    public void setUp() {
        Faculty f1 = DatabaseInitService.getFaculty1();
        Course c1 = DatabaseInitService.getCourse1();
        CourseOffering courseOffering = DatabaseInitService.getCourseOffering1(c1, f1);
        try {
            Mockito.when(courseOfferingService.getCourseOfferingById(1))
                    .thenReturn(courseOffering);
        } catch (CustomNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetCourseOfferingsById() throws Exception {
        mockMvc.perform(get("/student-view/course-offerings/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCourseOffering() throws Exception {
        Student student = new Student();
        student.setFirstName("John");

        when(courseRegistrationService.getCourseByStudent(student)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/student-view/course-offerings"))
                .andExpect(status().isOk())
                .andExpect(content().string("not found"));
    }

    @Test
    public void testGetCourseOfferingAttendance() throws Exception {
        Student student = new Student();
        student.setFirstName("John");

        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setId(1);
        courseOffering.setStartDate(LocalDate.now());
        courseOffering.setEndDate(LocalDate.now().plusDays(10));

        when(studentService.getAllStudents()).thenReturn(List.of(student));
        when(courseOfferingService.getCourseOfferingById(1)).thenReturn(courseOffering);
        when(attendanceRecordService.getAttendanceRecords(any(Student.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/student-view/course-offerings/1/attendance").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
