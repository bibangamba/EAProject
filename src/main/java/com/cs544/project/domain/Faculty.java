package com.cs544.project.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Faculty extends Person {

    @ElementCollection
    @CollectionTable(name = "FacultyHobby")
    List<String> hobbies;

    private String salutation;
}
