package com.example.crud.controller;

import com.example.crud.form.ContactForm;
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
    @Value("${email.admin}")
    private String emailTo;

    @Value("${email.user}")
    private String emailFrom;

    @Value("${email.user.password}")
    private String passEmailFrom;

    @PostMapping(value = "/userPage/contact")
    public ResponseEntity<String> contactToAdmin(@Valid @RequestBody ContactForm contactForm) {
        try {
            StringBuilder sb= new StringBuilder("From"+ contactForm.getFullName() +"\n");
            sb.append("Address: "+ contactForm.getEmail()+"\n");
            sb.append("Content: "+ contactForm.getContent());

            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(emailFrom, passEmailFrom));
            email.setSSLOnConnect(true);
            email.setFrom(emailFrom);
            email.setSubject("Contact from COSMETIC-SHOP");
            email.setMsg(sb.toString());
            email.addTo(emailTo);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Your email sent", HttpStatus.OK);
    }
    public static void main(String[] args) throws EmailException {
//        String to= "thuhuyen251998@gmail.com";
//        String from= "huyen251998@gmail.com";
//        String host= "stmp.gmail.com";
//        Properties properties= System.getProperties();
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        Session session= Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication(){
//                return new PasswordAuthentication("huyen251998@gmail.com", "AXuan487152");
//            }
//        });
//        session.setDebug(true);
//        try{
//            MimeMessage message= new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
//            message.setSubject("Subject");
//            message.setText("Content");
//            System.out.println("Sending....");
//            Transport.send(message);
//            System.out.println("Send message success");
//        }
//        catch (MessagingException e){
//            e.printStackTrace();
//        }
        String huyen= "huyen";
        StringBuilder sb= new StringBuilder();
        sb.append("Address: "+ huyen +"\n");
        sb.append("Content: "+ huyen);
        System.out.println(sb.toString());
    }
}
