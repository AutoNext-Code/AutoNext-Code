package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        if (to == null || to.isEmpty() || subject == null || subject.isEmpty() || text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros del email no pueden ser nulos o vacíos");
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        javaMailSender.send(message);
        System.out.println("Correo enviado con éxito a: " + to);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        if (to == null || to.isEmpty() || subject == null || subject.isEmpty() || htmlContent == null || htmlContent.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros del email no pueden ser nulos o vacíos");
        }

        String footer = "<p style='margin-top: 20px; font-size: 12px; color: #666; text-align: center; padding: 10px; border-top: 1px solid #ccc;'>"
        + "Este correo electrónico ha sido enviado desde <a href='https://autonextcode.duckdns.org' style='text-decoration: none; color: #337ab7;'>autonextcode.duckdns.org</a>.<br/>"
        + "Si tienes alguna pregunta o inquietud, no dudes en hacérnoslo saber.<br>¡Gracias por confiar en nosotros!</p>" + "<h2 style='text-align: center;'><span style='color:#18B5D9;'>Auto</span><span style='color:#0067B8;'>Next</span></h2>";

        String htmlContentWithFooter = htmlContent + footer;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContentWithFooter, true);
        
        javaMailSender.send(message);
        System.out.println("Correo HTML enviado con éxito a: " + to);
    }
}