package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.CourseOfferingType;
import com.cs544.project.domain.Faculty;
import com.cs544.project.dto.request.CourseOfferingRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.CourseOfferingRepository;
import com.cs544.project.security.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseOfferingServiceTests {
    @Autowired
    CourseOfferingService courseOfferingService;
    @MockBean
    CourseOfferingRepository courseOfferingRepository;
    @MockBean
    CourseService courseService;
    @MockBean
    FacultyService facultyService;
    LocalDate queryDate = LocalDate.of(2024, 6, 28);

    @Before
    public void setUp() throws CustomNotFoundException {
        Course course1 = DatabaseInitService.getCourse1();
        Faculty faculty1 = DatabaseInitService.getFaculty1();
        CourseOffering courseOffering = DatabaseInitService.getCourseOffering1(course1, faculty1);
        CourseOffering courseOffering2 = DatabaseInitService.getCourseOffering2(DatabaseInitService.getCourse2(course1), DatabaseInitService.getFaculty2SysAdmin());

        // Mock responses for courseOfferingRepository
        Mockito.when(courseOfferingRepository.findCourseOfferingByDate(queryDate)).thenReturn(List.of(courseOffering));
        Mockito.when(courseOfferingRepository.findById(1)).thenReturn(Optional.of(courseOffering));
        Mockito.when(courseOfferingRepository.findById(2)).thenReturn(Optional.of(courseOffering2));
        Mockito.when(courseOfferingRepository.findAll()).thenReturn(List.of(courseOffering, courseOffering2));
        Mockito.when(courseOfferingRepository.save(any(CourseOffering.class))).thenReturn(courseOffering);
        Mockito.doNothing().when(courseOfferingRepository).deleteById(anyInt()); // Assuming no return value

        // Mock responses for courseService
        Mockito.when(courseService.getCourseById(1)).thenReturn(DatabaseInitService.getCourse1());
        Mockito.when(courseService.getCourseById(2)).thenReturn(DatabaseInitService.getCourse2(course1));

        // Mock responses for facultyService
        Mockito.when(facultyService.getFacultyById(1)).thenReturn(DatabaseInitService.getFaculty1());
        Mockito.when(facultyService.getFacultyById(2)).thenReturn(DatabaseInitService.getFaculty2SysAdmin());
    }

    @Test
    public void testFindCourseOfferingByDate() {
        Collection<CourseOffering> courseOfferings = courseOfferingService.getCourseOfferingsByDate(queryDate);
        assertFalse(courseOfferings.isEmpty());
    }

    @Test
    public void testGetCourseOfferingById() throws CustomNotFoundException {
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(1);
        assertNotNull(courseOffering);
    }

    @Test
    public void testGetAllCourseOfferings() {
        Collection<CourseOffering> courseOfferings = courseOfferingService.getAllCourseOfferings();
        assertFalse(courseOfferings.isEmpty());
    }

    @Test
    public void testCreateCourseOffering() throws CustomNotFoundException {
        CourseOfferingRequest request = new CourseOfferingRequest();
        request.setCredits(3.0f);
        request.setCapacity(50);
        request.setRoom("LH-101");
        request.setStartDate(LocalDate.of(2024, 5, 27));
        request.setEndDate(LocalDate.of(2024, 6, 20));
        request.setType(CourseOfferingType.FULL_TIME);
        request.setFacultyId(1);
        request.setCourseId(1);

        CourseOffering createdCourseOffering = courseOfferingService.createCourseOffering(request);

        // Assertions
        assertNotNull(createdCourseOffering);
        assertEquals(request.getCredits(), createdCourseOffering.getCredits(), 0.01);
        assertEquals(request.getCapacity(), createdCourseOffering.getCapacity());
        assertEquals(request.getRoom(), createdCourseOffering.getRoom());
        assertEquals(request.getStartDate(), createdCourseOffering.getStartDate());
        assertEquals(request.getEndDate(), createdCourseOffering.getEndDate());
        assertEquals(request.getType(), createdCourseOffering.getCourseOfferingType());
    }

    @Test
    public void testUpdateCourseOfferingById() throws CustomNotFoundException {
        CourseOfferingRequest request = new CourseOfferingRequest();
        request.setCredits(4.0f);
        request.setCapacity(60);
        request.setRoom("B202");
        request.setStartDate(LocalDate.of(2024, 7, 15));
        request.setEndDate(LocalDate.of(2024, 8, 20));
        request.setType(CourseOfferingType.PART_TIME);

        CourseOffering updatedOffering = courseOfferingService.updateCourseOfferingById(1, request);

        // Assertions
        assertNotNull(updatedOffering);
        assertEquals(request.getCredits(), updatedOffering.getCredits(), 0.01);
        assertEquals(request.getCapacity(), updatedOffering.getCapacity());
        assertEquals(request.getRoom(), updatedOffering.getRoom());
        assertEquals(request.getStartDate(), updatedOffering.getStartDate());
        assertEquals(request.getEndDate(), updatedOffering.getEndDate());
        assertEquals(request.getType(), updatedOffering.getCourseOfferingType());
    }

    @Test
    public void testDeleteCourseOffering() throws CustomNotFoundException {
        courseOfferingService.deleteCourseOffering(1);
        // Verify that deleteById was called with the correct ID
        Mockito.verify(courseOfferingRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void testMapToCourseOffering() throws CustomNotFoundException {
        CourseOfferingRequest request = new CourseOfferingRequest();
        request.setCredits(3.0f);
        request.setCapacity(50);
        request.setRoom("A101");
        request.setStartDate(LocalDate.of(2024, 7, 1));
        request.setEndDate(LocalDate.of(2024, 8, 1));
        request.setType(CourseOfferingType.FULL_TIME);
        request.setFacultyId(1);
        request.setCourseId(1);

        Course course = DatabaseInitService.getCourse1();
        course.setId(1);
        Faculty faculty = DatabaseInitService.getFaculty1();
        faculty.setId(1);
        CourseOffering courseOffering = CourseOffering.getCourseOfferingFromRequest(new CourseOffering(), request, course, faculty);

        // Assertions
        assertNotNull(courseOffering);
        assertEquals(request.getCredits(), courseOffering.getCredits(), 0.01);
        assertEquals(request.getCapacity(), courseOffering.getCapacity());
        assertEquals(request.getRoom(), courseOffering.getRoom());
        assertEquals(request.getStartDate(), courseOffering.getStartDate());
        assertEquals(request.getEndDate(), courseOffering.getEndDate());
        assertEquals(request.getType(), courseOffering.getCourseOfferingType());
        assertEquals(request.getFacultyId(), courseOffering.getFaculty().getId());
        assertEquals(request.getCourseId(), courseOffering.getCourse().getId());
    }

    @TestConfiguration
    class CourseOfferingServiceTestContextConfiguration {

        @Bean
        public CourseOfferingService courseOfferingService() {
            return new CourseOfferingService(courseOfferingRepository, courseService, facultyService);
        }
    }
}