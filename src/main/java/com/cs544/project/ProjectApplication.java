package com.cs544.project;

import com.cs544.project.integration.messaging.Sender;
import com.cs544.project.service.DatabaseInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableJpaRepositories(basePackages = "com.cs544.project.repository")
public class ProjectApplication implements CommandLineRunner {
    private final Sender sender;
    private final DatabaseInitService dbInitService;

    @Autowired
    public ProjectApplication(Sender sender, DatabaseInitService dbInitService) {
        this.sender = sender;
        this.dbInitService = dbInitService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.sender.sendMessage("Hueco Mundo");
        // add more samples to this method only, commit them so everyone has them too.
        // we can use it for unit testing too
        dbInitService.addSampleData();
    }
}
