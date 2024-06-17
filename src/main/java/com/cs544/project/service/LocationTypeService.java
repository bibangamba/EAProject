package com.cs544.project.service;

import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.LocationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationTypeService {
    @Autowired
    LocationTypeRepository locationTypeRepository;
    public LocationType getLocationTypeById(int id) throws CustomNotFoundException{
        Optional<LocationType> location =  locationTypeRepository.findById(id);
        return location.orElseThrow(() -> new CustomNotFoundException("Could not find locationType with id=:" + id));
    }
}
