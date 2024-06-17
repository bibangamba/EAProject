package com.cs544.project.controller.sysadmin;


import com.cs544.project.adapter.LocationAdapter;
import com.cs544.project.domain.Location;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.response.LocationDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin/locations")
public class LocationController {
    @Autowired
    LocationService locationService;
    @GetMapping()
    ResponseEntity<?> getLocations(){
        Collection<Location> locations =  locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @PostMapping()
    ResponseEntity<?> addLocation(@RequestBody @Valid LocationCreateRequest locationCreateRequest) throws CustomNotFoundException {
        Location savedLocation =  locationService.addLocation(locationCreateRequest);
        LocationDto savedLocationDto = LocationAdapter.INSTANCE.toDto(savedLocation);
        return ResponseEntity.ok(savedLocation);
    }

    @PatchMapping()
    ResponseEntity<?> updateLocation(@RequestBody Location location){
        Location addedLocation =  locationService.updateLocation(location);
        return ResponseEntity.ok(addedLocation);
    }

    @DeleteMapping("/{locationId}")
    ResponseEntity<?> updateLocation(@PathVariable("locationId") long id) throws CustomNotFoundException {
        //        locationService.deleteLocation(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}