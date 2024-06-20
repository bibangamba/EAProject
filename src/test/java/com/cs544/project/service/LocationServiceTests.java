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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTests {
    @Autowired
    LocationService locationService;
    @MockBean
    LocationRepository locationRepository;

    @MockBean
    LocationTypeRepository locationTypeRepository;

    @MockBean
    AttendanceRecordRepository attendanceRecordRepository;

    @Test
    public void testGetAll_Success() {
        Location location = Location.getSampleData();
        Mockito.when(locationRepository.findAll()).thenReturn(List.of(location));

        Collection<Location> found = locationService.getAll();

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    public void testGet_Success() throws CustomNotFoundException {
        Integer id = 1;
        Location location = Location.getSampleData();
        location.setId(id);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Location found = locationService.getById(id);

        assertEquals(id, found.getId());
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    public void testGet_InvalidId() {
        Integer id = 1;

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.getById(id);
        });

        assertEquals("Could not find locationType with id=:" + id, exception.getMessage());
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    public void testCreate_Success() throws CustomNotFoundException {
        Integer locationTypeId = 1;
        LocationCreateRequest request = getLocationCreateRequest();
        request.setLocationTypeId(locationTypeId);

        LocationType locationType = new LocationType();
        locationType.setId(locationTypeId);

        Location location = LocationAdapter.INSTANCE.toEntity(request);
        location.setLocationType(locationType);

        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.of(locationType));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.create(request);

        assertNotNull(createdLocation);
        assertEquals(location.getId(), createdLocation.getId());
        assertEquals(locationTypeId, createdLocation.getLocationType().getId());

        verify(locationTypeRepository, times(1)).findById(locationTypeId);
        verify(locationRepository, times(1)).save(location);
    }




    @Test
    public void testCreate_LocationTypeNotFound() throws CustomNotFoundException {
        Integer locationTypeId = 1;
        LocationCreateRequest request = getLocationCreateRequest(); // Assume this is a valid request object
        request.setLocationTypeId(locationTypeId);

        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.create(request);
        });

        assertEquals("The LocationType with id=" + locationTypeId + " not found!", exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class)); // Ensure save is never called
    }

    @Test
    public void testUpdate_Success() throws CustomNotFoundException {
        Integer id = 1;
        Integer locationTypeId = 1;

        Location location = Location.getSampleData();
        location.setId(id);

        LocationPatchRequest locationPatchRequest = getLocationPatchRequest();
        LocationType locationType = new LocationType();
        locationType.setId(locationTypeId);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.of(locationType));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location updatedLocation = locationService.patch(id, locationPatchRequest);

        assertNotNull(updatedLocation);
        assertEquals(locationPatchRequest.getName(), updatedLocation.getName());
        assertEquals(locationPatchRequest.getCapacity(), updatedLocation.getCapacity().intValue());
        assertEquals(locationPatchRequest.getLocationTypeId(), updatedLocation.getLocationType().getId().intValue());

        verify(locationRepository, times(1)).findById(id);
        verify(locationTypeRepository, times(1)).findById(locationTypeId);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    public void testUpdateLocation_NotFound() {
        int pathId = 1;
        Location locationDetails = Location.getSampleData();

        LocationPatchRequest locationPatchRequest = getLocationPatchRequest();

        when(locationRepository.findById(pathId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.patch(pathId, any(LocationPatchRequest.class));
        });

        assertEquals("Could not find locationType with id=:" + pathId, exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class));
    }



    @Test
    public void testUpdate_LocationTypeNotFound() throws CustomNotFoundException {
        Integer pathId = 1;
        Integer locationTypeId = 1;
        LocationPatchRequest locationPatchRequest = getLocationPatchRequest(); // Assume this is a valid request object
        locationPatchRequest.setLocationTypeId(locationTypeId);

        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.patch(pathId,locationPatchRequest);
        });

        assertEquals("Could not find locationType with id=:" + locationTypeId, exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class)); // Ensure save is never called
    }

    @Test
    public void testUpdateCreate_Success() throws CustomNotFoundException {
        Integer id = 1;
        Integer locationTypeId = 1;

        Location location = Location.getSampleData();
        location.setId(id);

        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        LocationType locationType = new LocationType();
        locationType.setId(locationTypeId);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.of(locationType));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location updatedLocation = locationService.put(id, locationCreateRequest);

        assertNotNull(updatedLocation);
        assertEquals(locationCreateRequest.getName(), updatedLocation.getName());
        assertEquals(locationCreateRequest.getCapacity(), updatedLocation.getCapacity().intValue());
        assertEquals(locationCreateRequest.getLocationTypeId(), updatedLocation.getLocationType().getId().intValue());

        verify(locationRepository, times(1)).findById(id);
        verify(locationTypeRepository, times(1)).findById(locationTypeId);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    public void testUpdateCreateLocation_NotFound() {
        int pathId = 1;
        Location locationDetails = Location.getSampleData();

        when(locationRepository.findById(pathId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.put(pathId, any(LocationCreateRequest.class));
        });

        assertEquals("Could not find locationType with id=:" + pathId, exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class));
    }



    @Test
    public void testUpdateCreate_LocationTypeNotFound() throws CustomNotFoundException {
        Integer pathId = 1;
        Integer locationTypeId = 1;
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest(); // Assume this is a valid request object
        locationCreateRequest.setLocationTypeId(locationTypeId);

        when(locationTypeRepository.findById(locationTypeId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.put(pathId,locationCreateRequest);
        });

        assertEquals("Could not find locationType with id=:" + locationTypeId, exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class)); // Ensure save is never called
    }


    public LocationPatchRequest getLocationPatchRequest() {
        LocationPatchRequest request = new LocationPatchRequest();
        request.setName("name");
        request.setCapacity(10);
        request.setLocationTypeId(1);
        return request;
    }


    public  LocationCreateRequest getLocationCreateRequest(){
        LocationCreateRequest locationCreateRequest = new LocationCreateRequest();
        locationCreateRequest.setName("name");
        locationCreateRequest.setCapacity(10);
        locationCreateRequest.setLocationTypeId(1);
        return locationCreateRequest;
    }

    @Test
    public void testDelete_Success() throws CustomNotFoundException {
        Integer id = 1;

        Location location = new Location();
        location.setId(id);

        List<AttendanceRecord> attendanceRecord = DatabaseInitService.generateAttendanceRecord(
                DatabaseInitService.getStudent1(DatabaseInitService.getFaculty1()),
                DatabaseInitService.getLocation1(DatabaseInitService.getLectureHall()),
                LocalDate.now(),
                LocalDate.now(),
                LocalTime.of(13, 35),
                0);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        when(attendanceRecordRepository.getByLocation(location)).thenReturn(attendanceRecord);
        doNothing().when(locationRepository).delete(location);

        locationService.delete(id);

        verify(locationRepository, times(1)).findById(id);
        verify(attendanceRecordRepository, times(1)).getByLocation(location);
        verify(locationRepository, times(1)).delete(location);
    }

    @Test
    public void testDelete_NotFound() throws CustomNotFoundException{
        Integer pathId = 1;
        doNothing().when(locationRepository).deleteById(pathId);

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            locationService.put(pathId, any(LocationCreateRequest.class));
        });
        assertEquals("Could not find locationType with id=:" + pathId, exception.getMessage());
        verify(locationRepository, never()).delete(any(Location.class)); // Ensure save is never called
    }

}
