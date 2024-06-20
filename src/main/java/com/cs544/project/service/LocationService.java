package com.cs544.project.service;

import com.cs544.project.adapter.LocationAdapter;
import com.cs544.project.domain.AttendanceRecord;
import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.AttendanceRecordRepository;
import com.cs544.project.repository.LocationRepository;
import com.cs544.project.repository.LocationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Validated
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationTypeRepository locationTypeRepository;

    @Autowired
    AttendanceRecordRepository attendanceRecordRepository;



    public Collection<Location> getAll(){
        return locationRepository.findAll();
    }

    public Collection<Location> getByLocationType(LocationType locationType){
        return locationRepository.findByLocationType(locationType);
    }

    public Location getById(Integer id) throws CustomNotFoundException{
        Optional<Location> location =  locationRepository.findById(id);
        return location.orElseThrow(() -> new CustomNotFoundException("Could not find locationType with id=:" + id));
    }

    public Location create(LocationCreateRequest locationCreateRequest) throws CustomNotFoundException{
        Location location = LocationAdapter.INSTANCE.toEntity(locationCreateRequest);
        setLocationType(locationCreateRequest.getLocationTypeId(), location);
        // TODO: Set Audit Data
        return locationRepository.save(location);
    }

    public Location updateCreate(Integer id, LocationCreateRequest locationCreateRequest) throws CustomNotFoundException{
        Location location = getById(id);
        LocationAdapter.INSTANCE.updateEntityWithRequest(locationCreateRequest, location);
        setLocationType(locationCreateRequest.getLocationTypeId(), location);
        return locationRepository.save(location);
    }

    public Location update(Integer id, LocationPatchRequest locationPatchRequest) throws CustomNotFoundException{
        Location location = getById(id);
        LocationAdapter.INSTANCE.updateEntityWithRequest(locationPatchRequest, location);
        setLocationType(locationPatchRequest.getLocationTypeId(), location);
        return locationRepository.save(location);
    }

    public void delete(Integer id) throws CustomNotFoundException{
        Location location = getById(id);
        Collection<AttendanceRecord> attendanceRecords = attendanceRecordRepository.getByLocation(location);
        for(AttendanceRecord attendanceRecord: attendanceRecords){
            attendanceRecord.setLocation(null);
            attendanceRecordRepository.save(attendanceRecord);
        }
        locationRepository.delete(location);
    }

    private void setLocationType(Integer locationTypeId, Location location) throws CustomNotFoundException{
        if(locationTypeId == null) return ;
        LocationType locationType = locationTypeRepository.findById(locationTypeId)
                .orElseThrow(() -> new CustomNotFoundException("The LocationType with id="+ locationTypeId + " not found!"));
        location.setLocationType(locationType);
    }
}
