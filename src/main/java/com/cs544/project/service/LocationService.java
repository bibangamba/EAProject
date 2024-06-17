package com.cs544.project.service;

import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.AttendanceRecordRepository;
import com.cs544.project.repository.LocationRepository;
import com.cs544.project.repository.LocationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationTypeRepository locationTypeRepository;

    @Autowired
    AttendanceRecordRepository attendanceRecordRepository;



    public Collection<Location> getLocations(){
        return locationRepository.findAll();
    }

    public Collection<Location> getByLocationType(LocationType locationType){
        return locationRepository.findByLocationType(locationType);
    }

    public Location get(long id) throws CustomNotFoundException{
        Optional<Location> location =  locationRepository.findById(id);
        return location.orElseThrow(() -> new CustomNotFoundException("Could not find locationType with id=:" + id));
    }

    public Location addLocation(LocationCreateRequest locationCreateRequest) throws CustomNotFoundException{
        long locationTypeId = locationCreateRequest.getLocationTypeId();
        LocationType locationType = locationTypeRepository.findById(locationTypeId)
                .orElseThrow(() -> new CustomNotFoundException("The LocationType with id="+ locationTypeId + " not found!"));
        Location location = new Location();
        location.setName(locationCreateRequest.getName());
        location.setCapacity(locationCreateRequest.getCapacity());
        // TODO: Set Audit Data
        location.setLocationType(locationType);
        System.out.println(location);
        return locationRepository.save(location);
    }

    public Location updateLocation(Location location){
        return locationRepository.save(location);
    }

    public void delete(long id) throws CustomNotFoundException{
        Location location = get(id);
        Collection<AttendanceRecord> attendanceRecords = attendanceRecordRepository.getByLocation(location);
        for(AttendanceRecord attendanceRecord: attendanceRecords){
            attendanceRecord.setLocation(null);
            attendanceRecordRepository.save(attendanceRecord);
        }
        locationRepository.delete(location);
    }
}
