package com.cs544.project.dto.response;

import lombok.Data;

@Data
public class LocationTypeDto {
    private int id;
    private String type;
    private AuditDataDto auditData;
}
