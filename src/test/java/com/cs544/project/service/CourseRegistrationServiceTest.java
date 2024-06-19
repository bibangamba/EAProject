package com.cs544.project.service;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import com.cs544.project.repository.CourseRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRegistrationServiceTest {

    @Mock
    private CourseRegistrationRepository courseRegistrationRepository;

    @Mock
    private CourseOfferingRepository courseOfferingRepository;

    @InjectMocks
    private CourseRegistrationService courseRegistrationService;

    private Student student;
    private CourseOffering courseOffering;
    private CourseRegistration courseRegistration;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1);
        student.setFirstName("John");
        student.setLastName("Doe");

        courseOffering = new CourseOffering();
        courseOffering.setId(1);

        courseRegistration = new CourseRegistration();
        courseRegistration.setId(1);
        courseRegistration.setStudent(student);
        courseRegistration.setCourseOffering(courseOffering);
    }

    @Test
    public void testGetCourseByStudent() {
        when(courseRegistrationRepository.findAllByStudent(any(Student.class)))
                .thenReturn(Collections.singletonList(courseRegistration));

        Collection<StudentCourse> studentCourses = courseRegistrationService.getCourseByStudent(student);

        assertNotNull(studentCourses);
        assertEquals(1, studentCourses.size());
        assertEquals(student.getFullName(), studentCourses.iterator().next().getFullName());
    }

    @Test
    public void testGetCourseRegistrationByCourseOfferingId() throws CustomNotFoundException {
        when(courseRegistrationRepository.findCourseRegistrationByCourseOfferingId(any(Integer.class)))
                .thenReturn(Collections.singletonList(courseRegistration));

        List<CourseRegistration> courseRegistrations = courseRegistrationService.getCourseRegistrationByCourseOfferingId(1);

        assertNotNull(courseRegistrations);
        assertEquals(1, courseRegistrations.size());
        assertEquals(courseOffering, courseRegistrations.get(0).getCourseOffering());
    }

    @Test
    public void testGetAllCourseOfferingData() throws CustomNotFoundException {
        when(courseOfferingRepository.findById(any(Integer.class))).thenReturn(Optional.of(courseOffering));
        when(courseRegistrationRepository.getAllByCourseOffering(any(CourseOffering.class)))
                .thenReturn(Collections.singletonList(courseRegistration));

        Collection<CourseRegistration> courseRegistrations = courseRegistrationService.getAllCourseOfferingData(1);

        assertNotNull(courseRegistrations);
        assertEquals(1, courseRegistrations.size());
        assertEquals(courseOffering, courseRegistrations.iterator().next().getCourseOffering());
    }

    @Test
    public void testGetAllCourseOfferingData_NotFound() {
        when(courseOfferingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            courseRegistrationService.getAllCourseOfferingData(1);
        });

        assertNotNull(exception);
    }
}
