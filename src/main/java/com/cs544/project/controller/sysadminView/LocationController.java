package com.cs544.project.controller.sysadminView;


import com.cs544.project.adapter.LocationAdapter;
import com.cs544.project.domain.Location;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.dto.response.LocationDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.Collection;

@RestController("sys-admin-location")
@RequestMapping("/sys-admin/locations")
@Validated
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping()
    ResponseEntity<?> getAll(){
        Collection<Location> locations =  locationService.getAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    ResponseEntity<?>   getById(@PathVariable Integer id) throws  CustomNotFoundException{
        Location location =  locationService.getById(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping()
    ResponseEntity<?> add(@Valid @RequestBody LocationCreateRequest locationCreateRequest) throws CustomNotFoundException {
        Location savedLocation =  locationService.create(locationCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(LocationAdapter.INSTANCE.toDto(savedLocation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> put(@PathVariable Integer id, @Valid @RequestBody LocationCreateRequest locationCreateRequest) throws CustomNotFoundException {
        Location savedLocation = locationService.put(id, locationCreateRequest);
        return ResponseEntity.created(URI.create("/sys-admin/locations/" + savedLocation.getId()))
                .body(LocationAdapter.INSTANCE.toDto(savedLocation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocationDto> patch(@PathVariable Integer id, @Valid @RequestBody LocationPatchRequest locationPatchRequest) throws CustomNotFoundException {
        Location savedLocation = locationService.patch(id, locationPatchRequest);
        return ResponseEntity.created(URI.create("/sys-admin/locations/" + savedLocation.getId()))
                .body(LocationAdapter.INSTANCE.toDto(savedLocation));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) throws CustomNotFoundException {
        locationService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}