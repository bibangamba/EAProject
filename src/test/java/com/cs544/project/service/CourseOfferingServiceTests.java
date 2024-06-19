package com.cs544.project.service;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.repository.CourseOfferingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    /*
        createCourseOffering
        deleteCourseOffering
        getCourseOfferingsByDate
        getCourseOfferingById
        getAllCourseOfferings
        mapToCourseOffering
        updateCourseOfferingById
    */
    @Before
    public void setUp() {
        Course c1 = DatabaseInitService.getCourse1();
        Faculty f1 = DatabaseInitService.getFaculty1();
        CourseOffering courseOffering = DatabaseInitService.getCourseOffering1(c1, f1);
        Mockito.when(courseOfferingRepository.findCourseOfferingByDate(queryDate))
                .thenReturn(List.of(courseOffering));
    }

    @TestConfiguration
    class CourseOfferingServiceTestContextConfiguration {
        @Bean
        public CourseOfferingService courseOfferingService() {
            return new CourseOfferingService(courseOfferingRepository, courseService, facultyService);
        }
    }

    @Test
    public void testFindCourseOfferingByDate(){
        Collection<CourseOffering> courseOfferings = courseOfferingService.getCourseOfferingsByDate(queryDate);
        assertFalse(courseOfferings.isEmpty());
    }
}
