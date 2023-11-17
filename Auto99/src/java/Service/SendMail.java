/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.Properties;
import javax.mail.Message;
import static javax.mail.Message.RecipientType.TO;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import static javax.mail.Session.getDefaultInstance;
import javax.mail.Transport;
import static javax.mail.Transport.send;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class SendMail {

    /* Sending email */
    public void sendEmail(String email, String emailContent, String subject) {
        // Configure email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", true); // Enable SMTP authentication
        props.put("mail.smtp.starttls.enable", true); // Enable STARTTLS for secure communication
        props.put("mail.smtp.host", "smtp.gmail.com"); // Set the SMTP server host
        props.put("mail.smtp.port", "587"); // Set the SMTP server port for TLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Trust the SMTP server's SSL certificate
        props.setProperty("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2"); // Set SSL protocol version to TLSv1.2

        // Create a session with the configured properties and provide authentication
        Session session = getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("quizzeroproject@gmail.com", "dytmgttusivorrvq");
                // Replace with your email ID and password
            }
        });

        try {
            // Create a MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress("quizzeroproject@gmail.com")); // Change accordingly

            // Add the recipient's email address
            message.addRecipient(TO, new InternetAddress(email));

            // Set the email subject
            message.setSubject(subject, "UTF-8");

            // Set the email content as HTML
            message.setContent(emailContent, "text/html; charset=UTF-8");

            // Send the email message
            send(message);
        } catch (MessagingException e) {
            // Handle any messaging exceptions by throwing a runtime exception
            throw new RuntimeException(e);
        }
    }

}
