package com.cs544.project.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class LocationTypeCreateRequest {
    @NotNull()
    private String type;
}
