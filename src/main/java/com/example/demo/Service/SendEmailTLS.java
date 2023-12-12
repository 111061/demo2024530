package com.example.demo.Service;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class SendEmailTLS {

    public static void main(String[] args) {

        final String username = "a10443087jpp@gmail.com";
        final String password = "axcj awqp ebnn ioup";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("a10443087jpp@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("a10443087@gmail.com, chenyuhsuan@xs588874.xsrv.jp")
            );
            message.setSubject("Gmail TLS TEST");
            message.setText("AA,"
                    + "\n\n BBB");

            Transport.send(message);

            System.out.println("发送成功");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
