package com.cs544.project.service;

import com.cs544.project.domain.Location;
import com.cs544.project.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;
    public Collection<Location> getLocations(){
        return locationRepository.findAll();
    }
}
