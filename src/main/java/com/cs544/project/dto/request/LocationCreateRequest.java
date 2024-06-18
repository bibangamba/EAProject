package com.cs544.project.dto.request;

import com.cs544.project.utils.validators.ValidLocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * This class is used to accept request from the client with validation
 * to add Location. 
 */
@Data
@ValidLocationType
public class LocationCreateRequest {
    @NotNull(message= "capacity cannot be null")
    private Integer capacity;
    @NotBlank(message= "name cannot be null")
    private String name;
    private Integer locationTypeId;
    private LocationTypeCreateRequest locationType;
}
