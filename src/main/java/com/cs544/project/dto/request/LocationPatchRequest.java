package com.cs544.project.dto.request;

import lombok.Data;

@Data
public class LocationPatchRequest {
        private Integer capacity;
        private String name;
        private Integer locationTypeId;
}
