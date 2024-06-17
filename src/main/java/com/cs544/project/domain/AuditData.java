package com.cs544.project.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Embeddable
public class AuditData {
    @CreationTimestamp
    LocalDateTime createdOn;
    @UpdateTimestamp
    LocalDateTime updatedOn;
    String createdBy;
    String updatedBy;
}
