package com.cs544.project.controller.sysadminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.Student;
import com.cs544.project.controller.sysadminView.StudentController;
import com.cs544.project.repository.StudentRepository;
import com.cs544.project.service.DatabaseInitService;
import com.cs544.project.service.StudentService;
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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
@Import(ReusableBeansTestConfiguration.class)
public class StudentControllerTest {

    @MockBean
    DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;


    @MockBean
    private StudentRepository studentRepository;

    private Student student;

    @Before
    public void setUp() {
        student = new Student();
        student.setId(1);
        student.setStudentID("123");
        student.setFirstName("John");
        student.setLastName("Doe");
    }

    @Test
    public void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student));

        mockMvc.perform(get("/sys-admin/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(student.getLastName()));
    }

    @Test
    public void testGetStudentById() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/sys-admin/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(student.getLastName()));
    }

    @Test
    public void testGetStudentById_NotFound() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/sys-admin/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/sys-admin/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(student.getLastName()));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(eq(1), any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/sys-admin/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(student.getLastName()));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/sys-admin/students/1"))
                .andExpect(status().isNoContent());
    }
}
