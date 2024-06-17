package com.cs544.project.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationCreateRequest {
    @NotNull(message= "capacity cannot be null")
    private int capacity;
    @NotNull(message= "name cannot be null")
    private String name;
    @NotNull(message= "locationTypeId cannot be null")
    private int locationTypeId;
}
