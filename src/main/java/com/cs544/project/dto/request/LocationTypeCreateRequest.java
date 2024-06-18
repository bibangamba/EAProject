package com.cs544.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationTypeCreateRequest {
    @NotBlank(message = "type cannot be null")
    private String type;
}
