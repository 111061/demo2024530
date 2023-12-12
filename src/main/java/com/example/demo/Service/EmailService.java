package com.example.demo.Service;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class EmailService {

    public void sendEmail(String to, String subject, String content, String account, String password) {
        // 邮件发送属性配置
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // 使用 Gmail 的SMTP服务器
        props.put("mail.smtp.port", "587"); // Gmail 的SMTP服务器使用的端口号为 587
        props.put("mail.smtp.auth", "true"); // 需要请求认证
        props.put("mail.smtp.starttls.enable", "true"); // 使用 STARTTLS 安全连接
        // 更多配置...

        // 创建邮件会话
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(account,password);
            }
        });

        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // 发送邮件
            Transport.send(message);

            System.out.println("Mail sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace(); // 实际项目中可能需要更复杂的错误处理
        }
    }
}
