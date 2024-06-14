package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SecondaryTable(name = "PersonAccount", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Person {
    @Id
    @GeneratedValue
    private int id;

    private String firstName;
    private String lastName;

    @Column(name = "EmailAddress")
    private String emailAddress;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "GenderType")
    private GenderType genderType;

    @Column(table = "PersonAccount")
    private String username;

    @Column(table = "PersonAccount")
    private String password;

    @Column(table = "PersonAccount")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private AuditData auditData;
}
