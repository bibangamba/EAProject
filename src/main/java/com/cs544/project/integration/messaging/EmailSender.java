package com.cs544.project.integration.messaging;

import com.cs544.project.domain.Email;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class EmailSender {

    private final JmsTemplate jmsTemplate;
    @Value("${app.queues.email_queue}")
    private String sampleQueue;

    public EmailSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendEmail(Email email) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String emailAsString = objectMapper.writeValueAsString(email);
        jmsTemplate.convertAndSend(sampleQueue, emailAsString);
    }
}
