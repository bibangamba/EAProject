package com.cs544.project.service;

import com.cs544.project.adapter.LocationTypeAdapter;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.LocationRepository;
import com.cs544.project.repository.LocationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class LocationTypeService {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    LocationTypeRepository locationTypeRepository;
    public LocationType get(Integer id) throws CustomNotFoundException{
        Optional<LocationType> location =  locationTypeRepository.findById(id);
        return location.orElseThrow(() -> new CustomNotFoundException("Could not find locationType with id=:" + id));
    }

    public Collection<LocationType> get() {
        return locationTypeRepository.findAll();
    }

    public LocationType add(LocationTypeCreateRequest locationTypeCreateRequest) {
        LocationType locationType=LocationTypeAdapter.INSTANCE.toEntity(locationTypeCreateRequest);
        return locationTypeRepository.save(locationType);
    }


    public void delete(Integer id) throws CustomNotFoundException{
        LocationType locationType = get(id);
        Collection<Location> locations = locationRepository.findByLocationType(locationType);
        for(Location location: locations){
            location.setLocationType(null);
            locationRepository.save(location);
        }
        locationTypeRepository.delete(locationType);
    }
}
