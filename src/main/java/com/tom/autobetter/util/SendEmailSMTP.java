package com.tom.autobetter.util;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.MessageFormat;
import java.util.Properties;

@Component
public class SendEmailSMTP {

    // for example, smtp.mailgun.org
    private static final String SMTP_SERVER = "smtp.gmail.com";;

    public void sendMessage(String emailMessage, String title) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", SMTP_SERVER);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tom.eyre8770@gmail.com", System.getenv("PASS_ONE"));
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tom.eyre8770@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("tom.eyre8770@gmail.com"));
            message.setSubject(title);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailMessage, "text/plain");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}