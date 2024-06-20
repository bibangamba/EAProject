package com.cs544.project.service;

import com.cs544.project.domain.Student;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testGetStudentByStudentID() throws CustomNotFoundException {
        String studentID = "12345";
        Student student = new Student();
        student.setStudentID(studentID);

        when(studentRepository.findByStudentID(studentID)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentByStudentID(studentID);

        assertNotNull(foundStudent);
        assertEquals(studentID, foundStudent.getStudentID());
        verify(studentRepository, times(1)).findByStudentID(studentID);
    }

    @Test
    public void testGetStudentByStudentID_NotFound() {
        String studentID = "12345";

        when(studentRepository.findByStudentID(studentID)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> {
            studentService.getStudentByStudentID(studentID);
        });
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = List.of(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> foundStudents = studentService.getAllStudents();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() {
        int id = 1;
        Student student = new Student();
        student.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.getStudentById(id);

        assertTrue(foundStudent.isPresent());
        assertEquals(id, foundStudent.get().getId());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setFirstName("John");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.saveStudent(student);

        assertNotNull(savedStudent);
        assertEquals("John", savedStudent.getFirstName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUpdateStudent() {
        int id = 1;
        Student studentDetails = new Student();
        studentDetails.setFirstName("Jane");
        studentDetails.setLastName("Doe");

        Student existingStudent = new Student();
        existingStudent.setId(id);
        existingStudent.setFirstName("John");

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        Student updatedStudent = studentService.updateStudent(id, studentDetails);

        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals("Doe", updatedStudent.getLastName());
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    public void testUpdateStudent_NotFound() {
        int id = 1;
        Student studentDetails = new Student();
        studentDetails.setFirstName("Jane");

        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(studentDetails);

        Student updatedStudent = studentService.updateStudent(id, studentDetails);

        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals(id, updatedStudent.getId());
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(studentDetails);
    }

    @Test
    public void testDeleteStudent() {
        int id = 1;

        doNothing().when(studentRepository).deleteById(id);

        studentService.deleteStudent(id);

        verify(studentRepository, times(1)).deleteById(id);
    }
}
