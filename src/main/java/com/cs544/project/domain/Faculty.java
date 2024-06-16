package com.cs544.project.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Faculty extends Person {

  @ElementCollection
  @CollectionTable(name = "FacultyHobby")
  List<String> hobbies;

  @Column(name = "Salutation")
  private String salutation;
}
