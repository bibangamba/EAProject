package com.cs544.project.controller;

import com.cs544.project.domain.Person;
import com.cs544.project.repository.PersonAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class ApplicationController {

    @Autowired
    PersonAccountRepository personAccountRepository;

    public Person getCurrentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            Optional<Person> person = personAccountRepository.findByUsername(((UserDetails)principal).getUsername());
            return person.get();
        } else {
            return null;
        }
    }
}
