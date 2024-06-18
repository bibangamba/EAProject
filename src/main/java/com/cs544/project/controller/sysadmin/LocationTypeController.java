package com.cs544.project.controller.sysadmin;

import com.cs544.project.adapter.LocationTypeAdapter;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.dto.response.LocationTypeDto;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.LocationTypeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/sys-admin/location-type")
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
    ResponseEntity<?> create(@RequestBody @Valid LocationTypeCreateRequest locationCreateRequest) throws CustomNotFoundException {
        LocationType savedLocationType =  locationTypeService.add(locationCreateRequest);
        LocationTypeDto savedLocationTypeDto = LocationTypeAdapter.INSTANCE.toDto(savedLocationType);
        return ResponseEntity.ok(savedLocationType);
    }

//    @PatchMapping()
//    ResponseEntity<?> update(@RequestBody LocationType location){
//        LocationType addedLocationType =  locationTypeService.update(location);
//        return ResponseEntity.ok(addedLocationType);
//    }
//
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Integer id) throws CustomNotFoundException {
        locationTypeService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
