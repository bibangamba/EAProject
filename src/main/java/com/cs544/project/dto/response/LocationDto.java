package com.cs544.project.dto.response;

import lombok.Data;

@Data
public class LocationDto {
    private int id;
    private int capacity;
    private String name;
    private LocationTypeDto locationType;
    private AuditDataDto auditData;
}
