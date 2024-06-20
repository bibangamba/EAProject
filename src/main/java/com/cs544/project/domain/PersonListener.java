package com.cs544.project.domain;

import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PersonListener {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PrePersist
    public void prePersist(Person p) {
        if (p != null && p.getPassword() != null) {
            p.setPassword(passwordEncoder.encode(p.getPassword()));
        }
    }

}
