package com.cs544.project.integration.messaging;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.service.CourseOfferingService;
import com.cs544.project.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class EmailScheduler {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseOfferingService courseOfferingService;

    @Autowired
    EmailSender sender;
    
    // Run scheduled task every minute
    @Scheduled(fixedRate = 60000)
    public void sendEmailBeforeDeadline(){
        System.out.println("Running Scheduled Process...");
        Collection<CourseOffering> courseOfferings = courseOfferingService.getAllCourseOfferings();
        for(CourseOffering courseOffering: courseOfferings){
            LocalDate startDate = courseOffering.getStartDate();
            // Suppose deadline is 7 days before startDate at 22:00
            LocalDate deadline = startDate.minusDays(7);

            LocalDateTime deadlineWithTIme =  LocalDateTime.of(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth(), 17, 0);
            System.out.println("StartDate :" + startDate);
            System.out.println("Deadline  :" + deadlineWithTIme.minusHours(4));
            System.out.println("Now       :" + LocalDateTime.now());
            if(checkTiming(deadlineWithTIme, 4)){
                sendEmail(4,  courseOffering);
            }
            if(checkTiming(deadlineWithTIme, 8)){
                sendEmail(8, courseOffering);
            }
        }
    }

    private boolean checkTiming(LocalDateTime deadline, long hoursBeforeDeadline){
        // Get actual emailSendTime which is $hoursBeforDeadline ahead of headline
        LocalDateTime emailSendTime = deadline.minusHours(hoursBeforeDeadline);
        // Disregard seconds calculation
        LocalDateTime truncatedDeadline = emailSendTime.truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime truncatedNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        // Get duration in minutes
        long duration = Duration.between(truncatedNow, truncatedDeadline).toMinutes();
        return duration == 0;
    }

    private void sendEmail(int hours, CourseOffering courseOffering){
        Collection<EmailUser> students = studentService.getAllStudents().stream().map(x -> new EmailUser(x.getEmailAddress(),x.getFirstName() + " " +x.getLastName())).toList();
        Email email = new Email();
        email.setEmailTo(students);
        email.setCategory("Social");
        email.setSubject("Deadline for Registering Course: " + courseOffering.getCourse().getCourseName());
        email.setEmailFrom(new EmailUser("mailtrap@demomailtrap.com","Course Registration System"));
        email.setText("Dear Student,\n\nThe registration for your " +  courseOffering.getCourse().getCourseName() +   " is "+ hours+ " hours from now. Please, make sure you have registered for the course.\n\nThank you. \n\nRegards,\nCourse Registration System");
        try {
            System.out.println("Sending email......");
            sender.sendEmail(email);
        } catch(Exception e){
            System.out.println("Could not send Email" + e);
        }
    }



}
