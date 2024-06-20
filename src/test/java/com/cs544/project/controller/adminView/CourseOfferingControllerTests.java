package com.cs544.project.controller.adminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.DatabaseInitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseOfferingController.class)
@Import(ReusableBeansTestConfiguration.class)
@WithMockUser(username = "bibangamba", roles = "FACULTY")
public class CourseOfferingControllerTests {
    private final LocalDate queryDate = LocalDate.of(2024, 6, 28);
    @MockBean
    DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseOfferingService courseOfferingService; // mock because it's required by controller
    @MockBean
    private CourseRegistrationService courseRegistrationService; // also required by controller

    @Before
    public void setUp() throws CustomNotFoundException {
        Course c1 = DatabaseInitService.getCourse1();
        Faculty f1 = DatabaseInitService.getFaculty1();
        CourseOffering courseOffering = DatabaseInitService.getCourseOffering1(c1, f1);

        Mockito.when(courseOfferingService.getCourseOfferingsByDate(queryDate))
                .thenReturn(List.of(courseOffering));

        Mockito.when(courseOfferingService.getCourseOfferingById(1))
                .thenReturn(courseOffering);
    }

    @Test
    public void testGetCourseOfferingsByDate() throws Exception {
        mockMvc.perform(
                get("/admin-view/course-offerings")
                        .param("date", "2024-06-28")
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetCourseOfferingsByDateBadRequest() throws Exception {
        mockMvc.perform(
                        get("/admin-view/course-offerings?date=2024-06-282")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ParsingError\":\"Request parameter 'date' must be in " +
                        "the yyyy-mm-dd format. Received: 2024-06-282\"}"));
    }

    @Test
    public void testGetCourseOfferingsByOfferingId() throws Exception {
        mockMvc.perform(
                get("/admin-view/course-offerings/1")
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetCourseOfferingsByIdInvalidType() throws Exception {
        mockMvc.perform(
                        get("/admin-view/course-offerings/wqe")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"IncorrectParameterType\":\"Failed to convert value of " +
                        "type 'java.lang.String' to required type 'int'; For input string: \\\"wqe\\\"\"}"));
    }
}
