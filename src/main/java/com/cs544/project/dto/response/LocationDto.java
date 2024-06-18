package com.cs544.project.dto.response;

import lombok.Data;

@Data
public class LocationDto {
    private int id;
    private int capacity;
    private String name;
    private LocationTypeDto locationType;
    private AuditDataDto auditData;

    public LocationDto(String locationName) {
        this.name = locationName;
    }

    public LocationDto() {

    }
}
