package com.cs544.project.integration.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

  @Value("${app.queues.jms_sample_queue}")
  private String sampleQueue;

  private final JmsTemplate jmsTemplate;

  public Sender(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMessage(String message) {
    jmsTemplate.convertAndSend(sampleQueue, message);
  }
}
