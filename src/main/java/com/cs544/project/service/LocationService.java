package com.cs544.project.service;

import com.cs544.project.domain.Location;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;
    
    public Collection<Location> getLocations(){
        return locationRepository.findAll();
    }

    public Location addLocation(Location location){
        return locationRepository.save(location);
    }

    public Location updateLocation(Location location){
        return locationRepository.save(location);
    }

    public void deleteLocation(long id) throws CustomNotFoundException{
        Optional<Location> location = locationRepository.findById(id);
        locationRepository.delete(location.orElseThrow(() -> new CustomNotFoundException("Location with id="+id+ " not found")));
    }
}
