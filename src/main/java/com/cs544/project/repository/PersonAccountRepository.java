package com.cs544.project.repository;

import com.cs544.project.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonAccountRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p WHERE p.username = :username")
    Optional<Person> findByUsername(String username);

}
