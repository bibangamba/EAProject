package com.cs544.project.controller.sysadminView;

import com.cs544.project.adapter.LocationTypeAdapter;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationTypeService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//@RestController
//@RequestMapping("/sys-admin/location-type")
@Validated
public class LocationTypeController {
    @Autowired
    LocationTypeService locationTypeService;
    @GetMapping()
    ResponseEntity<?> getAll(){
        Collection<LocationType> locationTypes =  locationTypeService.get();
        return ResponseEntity.ok(locationTypes);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Integer id) throws  CustomNotFoundException{
        LocationType locationTypes =  locationTypeService.get(id);
        return ResponseEntity.ok(locationTypes);
    }

    @PostMapping()
    ResponseEntity<?> create(@RequestBody @Valid LocationTypeCreateRequest locationTypeCreateRequest) throws CustomNotFoundException {
        LocationType savedLocationType =  locationTypeService.create(locationTypeCreateRequest);
        return ResponseEntity.ok(LocationTypeAdapter.INSTANCE.toDto(savedLocationType));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> add(@Valid @RequestBody LocationTypeCreateRequest locationTypeCreateRequest, @PathParam("id") Integer id) throws CustomNotFoundException {
        LocationType savedLocationType =  locationTypeService.update(id, locationTypeCreateRequest);
        return ResponseEntity.ok(LocationTypeAdapter.INSTANCE.toDto(savedLocationType));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Integer id) throws CustomNotFoundException {
        locationTypeService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
