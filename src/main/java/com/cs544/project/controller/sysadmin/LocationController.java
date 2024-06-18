package com.cs544.project.controller.sysadmin;


import com.cs544.project.adapter.LocationAdapter;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
@RequestMapping("/sys-admin/locations")
@Validated
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Integer id) throws  CustomNotFoundException{
        Location location =  locationService.get(id);
        return ResponseEntity.ok(location);
    }
    @GetMapping()
    ResponseEntity<?> getAll(){
        Collection<Location> locations =  locationService.get();
        return ResponseEntity.ok(locations);
    }



    @PostMapping()
    ResponseEntity<?> add(@Valid @RequestBody LocationCreateRequest locationCreateRequest) throws CustomNotFoundException {
        Location savedLocation =  locationService.create(locationCreateRequest);
        return ResponseEntity.ok(LocationAdapter.INSTANCE.toDto(savedLocation));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> add(@Valid @RequestBody LocationCreateRequest locationCreateRequest, @PathParam("id") Integer id) throws CustomNotFoundException {
        Location savedLocation =  locationService.update(id, locationCreateRequest);
        return ResponseEntity.ok(LocationAdapter.INSTANCE.toDto(savedLocation));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> update(@Valid @RequestBody  LocationPatchRequest locationPatchRequest, @PathParam("id") Integer id) throws  CustomNotFoundException{
        Location addedLocation =  locationService.update(id, locationPatchRequest);
        return ResponseEntity.ok(LocationAdapter.INSTANCE.toDto(addedLocation));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Integer id) throws CustomNotFoundException {
        locationService.delete(id);
        return (ResponseEntity<?>) ResponseEntity.ok("Deleted");
    }
}