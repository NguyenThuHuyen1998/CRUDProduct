package com.example.crud.controller;

import com.example.crud.form.ContactForm;
import com.example.crud.service.SendEmailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class ContactController {
    private SendEmailService emailService;

    public ContactController(SendEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/userPage/contact")
    public ResponseEntity<String> contactToAdmin(@Valid @RequestBody ContactForm contactForm) throws EmailException {

        StringBuilder sb = new StringBuilder("From" + contactForm.getFullName() + "\n");
        sb.append("Address: " + contactForm.getEmail() + "\n");
        sb.append("Content: " + contactForm.getContent());

        if (emailService.contactToAdmin(sb.toString())) {
            return new ResponseEntity<>("Please check your email", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
