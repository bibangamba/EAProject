package com.cs544.project.controller;

import com.cs544.project.domain.Location;
import com.cs544.project.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin")
public class SysAdminController {
    @Autowired
    LocationService locationService;
    @GetMapping("/locations")
    ResponseEntity<?> getLocations(){
        Collection<Location> locations =  locationService.getLocations();
        return ResponseEntity.ok(locations);
    }
}
