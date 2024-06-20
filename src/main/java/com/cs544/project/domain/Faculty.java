package com.cs544.project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Faculty extends Person {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "FacultyHobby")
    List<String> hobbies;

    @Column(name = "Salutation")
    private String salutation;

    public Faculty() {
    }

    public Faculty(Person person, List<String> hobbies, String salutation) {
        super();
        this.hobbies = hobbies;
        this.salutation = salutation;
    }
}
