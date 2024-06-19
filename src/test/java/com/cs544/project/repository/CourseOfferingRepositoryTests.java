package com.cs544.project.repository;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.service.DatabaseInitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ReusableBeansTestConfiguration.class)
public class CourseOfferingRepositoryTests {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Test
    public void whenFindById_returnCourseOffering() {
        Course course = DatabaseInitService.getCourse3();
        Faculty faculty = DatabaseInitService.getFaculty1();
        CourseOffering courseOfferingToBeSaved = DatabaseInitService.getCourseOffering1(course, faculty);
        int id = testEntityManager.persist(courseOfferingToBeSaved).getId();
        testEntityManager.flush();
        CourseOffering retrievedCourseOffering = courseOfferingRepository.findById(id).orElseThrow();
        assertEquals(courseOfferingToBeSaved, retrievedCourseOffering);
    }

    @Test
    public void whenFindByDate_returnCourseOfferings() {
        LocalDate startDate = LocalDate.of(2024, 2, 20);
        LocalDate endDate = LocalDate.of(2024, 3, 15);

        Course course = DatabaseInitService.getCourse3();
        Faculty faculty = DatabaseInitService.getFaculty1();
        CourseOffering courseOfferingToBeSaved = DatabaseInitService.getCourseOffering1(course, faculty);

        courseOfferingToBeSaved.setStartDate(startDate);
        courseOfferingToBeSaved.setEndDate(endDate);

        testEntityManager.persist(courseOfferingToBeSaved);
        testEntityManager.flush();

        List<CourseOffering> courseOfferings = courseOfferingRepository.findCourseOfferingByDate(
                startDate.plusDays(1));
        assertFalse(courseOfferings.isEmpty());
        assertEquals(courseOfferingToBeSaved, courseOfferings.getFirst());
    }
}