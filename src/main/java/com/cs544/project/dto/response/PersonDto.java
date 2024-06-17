package com.cs544.project.dto.response;

import com.cs544.project.domain.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDto {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthdate;
    private GenderType genderType;
    private AuditDataDto auditData;
}
