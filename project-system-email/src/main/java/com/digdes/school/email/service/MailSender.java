package com.digdes.school.email.service;

import com.digdes.school.email.EmailContext;
import jakarta.mail.MessagingException;

public interface MailSender {

    void send(EmailContext context) throws MessagingException;
}
