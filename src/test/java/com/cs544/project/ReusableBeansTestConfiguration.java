package com.cs544.project;

import com.cs544.project.integration.messaging.EmailSender;
import com.cs544.project.service.DatabaseInitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ReusableBeansTestConfiguration {
    @MockBean
    private EmailSender sender;

    @MockBean
    private JmsTemplate jmsTemplate;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Bean
    public DatabaseInitService databaseInitService() {
        return new DatabaseInitService();
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
