package com.cs544.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Student extends Person {
  @Column(name = "Entry")
  private LocalDate entry;

  @Column(name = "StudentID")
  private String studentID;

  @Column(name = "AlternateID")
  private String alternateID;

  @Column(name = "ApplicantID")
  private String applicantID;

  @ManyToOne
  @JoinColumn(name = "FacultyAdviserID")
  private Faculty faculty;
}
