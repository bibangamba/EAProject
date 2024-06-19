package com.cs544.project;

import com.cs544.project.domain.Email;
import com.cs544.project.integration.messaging.EmailSender;
import com.cs544.project.service.DatabaseInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.time.LocalDateTime;
import java.util.Arrays;

@EnableJms
@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
    private final EmailSender sender;
    private final DatabaseInitService dbInitService;

    @Autowired
    public ProjectApplication(EmailSender sender, DatabaseInitService dbInitService) {
        this.sender = sender;
        this.dbInitService = dbInitService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.sender.sendEmail(Email.getSampleData());
        // add more samples to this method only, commit them so everyone has them too.
        // we can use it for unit testing too
        dbInitService.addSampleData();
    }


}
