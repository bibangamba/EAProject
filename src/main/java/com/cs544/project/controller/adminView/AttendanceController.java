package com.cs544.project.controller.adminView;

import com.cs544.project.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin-view")
public class AttendanceController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @GetMapping("/courseofferings/{offeringId}/attendance")
    public ResponseEntity<Resource> getAttendanceRecords(@PathVariable Long offeringId) {
        try {
            ByteArrayInputStream in = attendanceRecordService.exportAttendanceToExcel(offeringId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
            LocalTime today = LocalTime.now();
            String timeString = today.format(formatter);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=attendance_"+timeString+".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(in));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
