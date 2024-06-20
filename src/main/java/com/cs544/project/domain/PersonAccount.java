package com.cs544.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PersonAccount {
    private int id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
