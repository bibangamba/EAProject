package com.cs544.project.controller;

import com.cs544.project.domain.Person;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.PersonAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationController {

    @Autowired
    PersonAccountRepository personAccountRepository;

    public Person getCurrentPerson() throws CustomNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            Person person = personAccountRepository.findByUsername(((UserDetails) principal).getUsername()).orElseThrow(
                    () -> new CustomNotFoundException("No account found with username=" + ((UserDetails) principal).getUsername())
            );
            return person;
        } else {
            return null;
        }
    }
}
