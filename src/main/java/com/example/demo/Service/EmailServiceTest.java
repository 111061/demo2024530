package com.example.demo.Service;

public class EmailServiceTest {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        // 替换为实际的接收者邮件地址
        String toEmail = "a10443087@gmail.com";
        // 邮件主题
        String subject = "Test Email Subject";
        // 邮件内容
        String content = "Hello, this is a test email from Jakarta Mail API2.";
        String account ="a10443087jpp@gmail.com";
        String password ="axcj awqp ebnn ioup";

        // 调用发送邮件的服务方法
        emailService.sendEmail(toEmail, subject, content, account, password);
    }
}
