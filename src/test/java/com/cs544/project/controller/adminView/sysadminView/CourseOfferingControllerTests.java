package com.cs544.project.controller.adminView.sysadminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.adapter.CourseOfferingAdapter;
import com.cs544.project.controller.sysadminView.CourseOfferingController;
import com.cs544.project.domain.Course;
import com.cs544.project.domain.CourseOffering;
import com.cs544.project.domain.Faculty;
import com.cs544.project.dto.request.CourseOfferingRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.CourseRegistrationService;
import com.cs544.project.service.DatabaseInitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.cs544.project.domain.CourseOffering.getCourseOfferingFromRequest;
import static com.cs544.project.domain.CourseOfferingType.FULL_TIME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseOfferingController.class)
@Import(ReusableBeansTestConfiguration.class)
public class CourseOfferingControllerTests {
    private final LocalDate queryDate = LocalDate.of(2024, 6, 28);
    private final LocalDate startDate = LocalDate.of(2024, 2, 18);
    private final LocalDate endDate = LocalDate.of(2024, 3, 26);
    private final LocalDate earlierEndDate = LocalDate.of(2024, 1, 26);
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseOfferingService courseOfferingService; // mock because it's required by controller
    @MockBean
    private CourseRegistrationService courseRegistrationService; // also required by controller
    private Course course1;
    private Faculty faculty1;

    @Before
    public void setUp() throws CustomNotFoundException {
        course1 = DatabaseInitService.getCourse1();
        faculty1 = DatabaseInitService.getFaculty1();
        CourseOffering courseOffering = DatabaseInitService.getCourseOffering1(course1, faculty1);

        Mockito.when(courseOfferingService.getCourseOfferingsByDate(queryDate))
                .thenReturn(List.of(courseOffering));

        Mockito.when(courseOfferingService.getCourseOfferingById(1))
                .thenReturn(courseOffering);

        // mock create course offering
        CourseOffering createdCourseOffering = getCourseOfferingFromRequest(new CourseOffering(), getCourseOfferingRequest(), course1, faculty1);
        createdCourseOffering.setId(1);
        Mockito.when(courseOfferingService.createCourseOffering(getCourseOfferingRequest())).thenReturn(createdCourseOffering);

        // mock all course offerings
        List<CourseOffering> allCourseOfferings = List.of(
                DatabaseInitService.getCourseOffering1(course1, faculty1),
                DatabaseInitService.getCourseOffering2(DatabaseInitService.getCourse2(course1), faculty1),
                courseOffering
        );
        Mockito.when(courseOfferingService.getAllCourseOfferings()).thenReturn(allCourseOfferings);

        // mock update course offering
        CourseOffering updatedCourseOffering = DatabaseInitService.getCourseOffering1(
                course1, DatabaseInitService.getFaculty1());
        updatedCourseOffering.setCapacity(100);
        Mockito.when(courseOfferingService.updateCourseOfferingById(1, getCourseOfferingRequest()))
                .thenReturn(createdCourseOffering);

        // mock for deleting a course offering
        Mockito.doNothing().when(courseOfferingService).deleteCourseOffering(1);
    }

    private CourseOfferingRequest getCourseOfferingRequest() {
        CourseOfferingRequest createRequest = new CourseOfferingRequest();
        createRequest.setCredits(4.0f);
        createRequest.setCapacity(15);
        createRequest.setRoom("V11");
        createRequest.setStartDate(startDate);
        createRequest.setEndDate(endDate);
        createRequest.setType(FULL_TIME);
        createRequest.setCourseId(1);
        createRequest.setFacultyId(1);
        return createRequest;
    }

    @Test
    public void testGetCourseOfferingById() throws Exception {
        mockMvc.perform(
                get("/sys-admin/course-offerings/1")
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetCourseOfferingByIdBadRequest() throws Exception {
        mockMvc.perform(
                        get("/sys-admin/course-offerings/rqwgs")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"IncorrectParameterType\":\"Failed to convert " +
                        "value of type 'java.lang.String' to required type 'int'; For input string: \\\"rqwgs\\\"\"}"));

    }

    @Test
    public void testGetCourseOfferings() throws Exception {
        mockMvc.perform(get("/sys-admin/course-offerings"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddCourseOffering() throws Exception {
        CourseOffering createdCourseOffering = getCourseOfferingFromRequest(new CourseOffering(),
                getCourseOfferingRequest(), course1, faculty1);
        createdCourseOffering.setId(1);

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCourseOfferingRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        CourseOfferingAdapter.INSTANCE.toDto(createdCourseOffering)
                )));
        ;
    }

    @Test
    public void testAddCourseOfferingInvalidDateRange() throws Exception {
        CourseOfferingRequest offeringRequest = getCourseOfferingRequest();
        offeringRequest.setEndDate(earlierEndDate);

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offeringRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"endDate\":\"endDate must be after startDate\"}"));
    }

    @Test
    public void testAddCourseOfferingInvalidDateFormat() throws Exception {
        CourseOfferingRequest offeringRequest = getCourseOfferingRequest();
        offeringRequest.setEndDate(earlierEndDate);
        String jsonRequest = objectMapper.writeValueAsString(getCourseOfferingRequest());
        String editedJsonRequest = jsonRequest.replace("2024-02-18", "2024-02-18fasdg");

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editedJsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ParsingError\":\"JSON parse error: Cannot deserialize " +
                        "value of type `java.time.LocalDate` from String \\\"2024-02-18fasdg\\\": Failed to " +
                        "deserialize java.time.LocalDate: (java.time.format.DateTimeParseException) " +
                        "Text '2024-02-18fasdg' could not be parsed, unparsed text found at index 10\"}"));
    }

    @Test
    public void testAddCourseOfferingInvalidType() throws Exception {
        CourseOfferingRequest offeringRequest = getCourseOfferingRequest();
        offeringRequest.setEndDate(earlierEndDate);
        String jsonRequest = objectMapper.writeValueAsString(getCourseOfferingRequest());
        String editedJsonRequest = jsonRequest.replace("FULL_TIME", "FULLY");

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editedJsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ParsingError\":\"JSON parse error: Cannot deserialize " +
                        "value of type `com.cs544.project.domain.CourseOfferingType` " +
                        "from String \\\"FULLY\\\": not one of the values accepted for " +
                        "Enum class: [PART_TIME, ONLINE, FULL_TIME]\"}"));
    }

    @Test
    public void testAddCourseOfferingMissingValues() throws Exception {
        String output = """
                {
                    "facultyId": "facultyId cannot be zero or empty",
                    "credits": "credits must be at least 1",
                    "endDate": "endDate is a required field",
                    "startDate & endDate": "startDate and endDate must be provided and cannot be null",
                    "type": "type  can only be one of [PART_TIME, ONLINE, FULL_TIME]",
                    "courseId": "courseId cannot be zero or empty",
                    "startDate": "startDate is a required field",
                    "room": "room is a required field",
                    "capacity": "capacity must be at least 1"
                }
                """;

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CourseOfferingRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(output));
    }


    @Test
    public void testUpdateCourseOffering() throws Exception {
        CourseOffering updateCourseOffering = CourseOffering.getCourseOfferingFromRequest(
                new CourseOffering(), getCourseOfferingRequest(), course1, faculty1
        );
        updateCourseOffering.setId(1);

        mockMvc.perform(put("/sys-admin/course-offerings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCourseOfferingRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        CourseOfferingAdapter.INSTANCE.toDto(updateCourseOffering)
                )));
    }

    @Test
    public void testDeleteCourseOfferingById() throws Exception {
        mockMvc.perform(delete("/sys-admin/course-offerings/1"))
                .andExpect(status().isNoContent());
    }
}
