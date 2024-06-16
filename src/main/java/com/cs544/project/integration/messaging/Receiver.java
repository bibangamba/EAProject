package com.cs544.project.integration.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  @JmsListener(destination = "${app.queues.jms_sample_queue}")
  public void receiveMessage(String message) {
    System.out.printf("Message received:: %s", message);
  }
}
