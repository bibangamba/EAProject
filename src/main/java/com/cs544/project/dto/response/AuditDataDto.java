package com.cs544.project.dto.response;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditDataDto {
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
    String createdBy;
    String updatedBy;
}
