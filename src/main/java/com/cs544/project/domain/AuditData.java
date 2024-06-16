package com.cs544.project.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class AuditData {
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
    String createdBy;
    String updatedBy;
}
