package com.example.demo.DTO;

import java.util.List;

public class EmailDetails {
    private List<String> emails; // 收件人邮箱列表
    private String subject; // 邮件主题
    private String content; // 邮件内容
    private String account; // 发件人账号
    private String password; // 发件人密码

    // 无参构造器
    public EmailDetails() {
    }

    // 带所有参数的构造器
    public EmailDetails(List<String> emails, String subject, String content, String account, String password) {
        this.emails = emails;
        this.subject = subject;
        this.content = content;
        this.account = account;
        this.password = password;
    }

    // Getters
    public List<String> getEmails() {
        return emails;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
