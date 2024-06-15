package com.cs544.project.controller;

import com.cs544.project.domain.Location;
import com.cs544.project.dto.LocationDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/locations")
    ResponseEntity<?> addLocation(@RequestBody Location location){
        Location addedLocation =  locationService.addLocation(location);
        return ResponseEntity.ok(addedLocation);
    }

    @PatchMapping("/locations")
    ResponseEntity<?> updateLocation(@RequestBody LocationDto location){
        Location addedLocation =  locationService.updateLocation(location);
        return ResponseEntity.ok(addedLocation);
    }

    @DeleteMapping("/locations/{locationId}")
    ResponseEntity<?> updateLocation(@PathVariable("locationId") long id) throws CustomNotFoundException {
        locationService.deleteLocation(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
