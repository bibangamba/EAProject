package com.cs544.project.integration.messaging;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
public  class Email {
    private EmailUser emailFrom;
    private Collection<EmailUser> emailTo;
    private Collection<EmailUser> cc;
    private Collection<EmailUser> bcc;
    private String subject;
    private String text;
    private String category;
    private Date sentDate;

    public Email(){
        this.setSentDate(new Date());
    }
}