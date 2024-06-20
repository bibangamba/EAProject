package com.cs544.project.controller.sysadminView;

import com.cs544.project.ReusableBeansTestConfiguration;
import com.cs544.project.adapter.LocationAdapter;
import com.cs544.project.domain.Location;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.service.DatabaseInitService;
import com.cs544.project.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
@Import(ReusableBeansTestConfiguration.class)
public class LocationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @MockBean
    private DatabaseInitService databaseInitService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testGetAll_Success() throws Exception {
        Location location1 = Location.getSampleData();
        Location location2 = Location.getSampleData();
        location2.setId(2);
        when(locationService.getAll()).thenReturn(Arrays.asList(location1,location2));
        mockMvc.perform(get("/sys-admin/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(location1.getId()))
                .andExpect(jsonPath("$[1].id").value(location2.getId()));
    }

    @Test
    public void testGet_InvalidIdNotFound() throws Exception {
        when(locationService.getById(100)).thenThrow(new CustomNotFoundException("Location not found"));
        mockMvc.perform(get("/sys-admin/locations/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ErrorMessage").value("Location not found"));
    }

    @Test
    public void testGet_Success() throws Exception {
        Location location = Location.getSampleData();
        when(locationService.getById(1)).thenReturn(location);
        mockMvc.perform(get("/sys-admin/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(location.getId()));
    }

    @Test
    public void testCreate_Success() throws Exception {

        Location location = Location.getSampleData();
        location.setId(1);
        location.getLocationType().setId(1);
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();

        when(locationService.create(locationCreateRequest)).thenReturn(location);

        mockMvc.perform(post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        LocationAdapter.INSTANCE.toDto(location)
                )));
    }

    @Test
    public void testCreate_BadRequest() throws Exception {
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        locationCreateRequest.setName(null);
        locationCreateRequest.setCapacity(null);
        locationCreateRequest.setLocationType(new LocationTypeCreateRequest("name"));

        String jsonRequest = objectMapper.writeValueAsString(locationCreateRequest);

        mockMvc.perform(post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.locationCreateRequest").value("Either locationTypeId or locationType must be provided, not both"))
                .andExpect(jsonPath("$.name").value("name cannot be null"))
                .andExpect(jsonPath("$.capacity").value("capacity cannot be null"));
    }

    @Test
    public void testCreate_InvalidLocationTypeIdBadRequest() throws Exception {
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        locationCreateRequest.setLocationTypeId(0);

        String jsonRequest = objectMapper.writeValueAsString(locationCreateRequest);

        when(locationService.create(locationCreateRequest)).thenThrow(new CustomNotFoundException("Location Type Not Found"));

        mockMvc.perform(post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ErrorMessage").value("Location Type Not Found"));
    }

    @Test
    public void testCreate_NullLocationBadRequest() throws Exception {
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        locationCreateRequest.setLocationTypeId(null);
        locationCreateRequest.setLocationType(null);

        String jsonRequest = objectMapper.writeValueAsString(locationCreateRequest);

        mockMvc.perform(post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.locationCreateRequest").value("Either locationTypeId or locationType must be provided, not both"));
    }

    @Test
    public void testPut_Success() throws Exception {
        // Create sample location data
        Location location = Location.getSampleData();
        location.setId(1);
        location.getLocationType().setId(1);

        // Create request object
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();

        // Mock the service method
        when(locationService.put(1, locationCreateRequest)).thenReturn(location);

        // Perform the PUT request and validate the response
        mockMvc.perform(put("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        LocationAdapter.INSTANCE.toDto(location)
                )));
    }

    @Test
    public void testPut_NullLocationBadRequest() throws Exception {
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        locationCreateRequest.setLocationTypeId(null);
        locationCreateRequest.setLocationType(null);

        String jsonRequest = objectMapper.writeValueAsString(locationCreateRequest);

        mockMvc.perform(put("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.locationCreateRequest").value("Either locationTypeId or locationType must be provided, not both"));
    }

    @Test
    public void testPut_InvalidLocationTypeIdBadRequest() throws Exception {
        LocationCreateRequest locationCreateRequest = getLocationCreateRequest();
        locationCreateRequest.setLocationTypeId(0);

        String jsonRequest = objectMapper.writeValueAsString(locationCreateRequest);

        when(locationService.put(1, locationCreateRequest)).thenThrow(new CustomNotFoundException("Location Type Not Found"));

        mockMvc.perform(put("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ErrorMessage").value("Location Type Not Found"));
    }

    @Test
    public void testDelete_Success() throws Exception {
        mockMvc.perform(delete("/sys-admin/locations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDelete_InvalidIdNotFound() throws Exception {
        doThrow(new CustomNotFoundException("Location not found")).when(locationService).delete(1);
        mockMvc.perform(delete("/sys-admin/locations/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ErrorMessage").value("Location not found"));
    }

    @Test
    public void testPatch_Success() throws Exception {
        Location location = Location.getSampleData();
        location.setId(1);
        location.getLocationType().setId(1);

        LocationPatchRequest locationPatchRequest = getLocationPatchRequest();

        when(locationService.patch(1, locationPatchRequest)).thenReturn(location);

        mockMvc.perform(patch("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationPatchRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        LocationAdapter.INSTANCE.toDto(location)
                )));
    }

    public  LocationPatchRequest getLocationPatchRequest() {
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
}
