package com.cs544.project.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Embeddable
public class AuditData {
  LocalDateTime createdOn;
  LocalDateTime updatedOn;
  String createdBy;
  String updatedBy;
}
