package com.cs544.project.service;

import com.cs544.project.domain.Person;
import com.cs544.project.repository.PersonAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonAccountRepository personAccountRepository;

    public UserDetailsServiceImpl(PersonAccountRepository personAccountRepository) {
        this.personAccountRepository = personAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
        return User
                .withUsername(person.getUsername())
                .password(person.getPassword())
                .roles(person.getRole().name())
                .build();
    }
}
