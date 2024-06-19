package com.cs544.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private EmailUser emailFrom;
    private Collection<EmailUser> emailTo;
    private Collection<EmailUser> cc;
    private Collection<EmailUser> bcc;
    private String subject;
    private String text;
    private String category;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailUser {
        private String email;
        private String name;
    }
//    private List<Attachment> attachments;
    private Date sentDate;

    @Data
    public static class Attachment {
        private String fileName;
        private byte[] fileContent;
        private String mimeType;
    }


    public static Email getSampleData(){
        Email email = new Email();
        email.setEmailTo(Arrays.asList(new EmailUser("sourrab@gmail.com", "User1")));
        email.setEmailFrom(new EmailUser("mailtrap@demomailtrap.com", "User2"));
        email.setSubject("Subject of the Email");
        email.setText("Body of the Email");
        email.setCategory("Course Registration Deadline");
//        email.setCc(Arrays.asList(new EmailUser("cc1@example.com", "User3"), new EmailUser("cc2@example.com", "User4")));
//        email.setBcc(Arrays.asList(new EmailUser("bcc1@example.com", "User3"), new EmailUser("bcc2@example.com", "User4")));
        email.setSentDate(new Date());
        return email;
    }
}