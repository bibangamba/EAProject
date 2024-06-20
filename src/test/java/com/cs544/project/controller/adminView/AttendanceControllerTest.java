package com.cs544.project.controller.adminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.service.AttendanceRecordService;
import com.cs544.project.service.DatabaseInitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@Import(ReusableBeansTestConfiguration.class)
@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @MockBean
    DatabaseInitService databaseInitService; //required by application, but can mock because we don't need a working version


    @Test
    public void testGetAttendanceRecords() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
        when(attendanceRecordService.exportAttendanceToExcel(1L)).thenReturn(byteArrayInputStream);

        mockMvc.perform(get("/admin-view/course-offerings/1/attendance"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm")) + ".xlsx"));
    }

    @Test
    public void testGetAttendanceRecords_InternalServerError() throws Exception {
        when(attendanceRecordService.exportAttendanceToExcel(1L)).thenThrow(new IOException());

        mockMvc.perform(get("/admin-view/course-offerings/1/attendance"))
                .andExpect(status().isInternalServerError());
    }
}
