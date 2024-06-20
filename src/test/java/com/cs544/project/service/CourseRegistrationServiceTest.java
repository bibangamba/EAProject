package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseRegistration;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.StudentCourse;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import com.cs544.project.repository.CourseRegistrationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

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

    @Before
    public void setUp() {
        student = new Student();
        student.setId(1);
        student.setFirstName("John");
        student.setLastName("Doe");

        courseOffering = new CourseOffering();
        courseOffering.setId(1);
        courseOffering.setStartDate(LocalDate.of(2024, 5, 27));
        courseOffering.setEndDate(LocalDate.of(2024, 6, 20));
        courseOffering.setCapacity(50);
        courseOffering.setCredits(3.0f);
        Course course1 = new Course();
        course1.setCourseName("Introduction to Computer Science");
        course1.setCourseCode("CS101");
        course1.setCredits(3.0f);
        courseOffering.setCourse(course1);

        courseRegistration = new CourseRegistration();
        courseRegistration.setId(1);
        courseRegistration.setStudent(student);
        courseRegistration.setCourseOffering(courseOffering);
    }

    @Test
    public void testGetCourseByStudent() {
        when(courseRegistrationRepository.findAllByStudent(student))
                .thenReturn(Collections.singletonList(courseRegistration));

        System.out.println(student);
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
}
