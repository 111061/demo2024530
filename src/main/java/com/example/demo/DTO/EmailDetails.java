package com.example.demo.DTO;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class EmailDetails {

    @NotEmpty(message = "Email list cannot be empty")
    private List<@Email(message = "Invalid email address") String> emails;

    @NotNull(message = "Subject cannot be null")
    private String subject;

    @NotNull(message = "Content cannot be null")
    private String content;

    @NotNull(message = "Account cannot be null")
    private String account;

    @NotNull(message = "Password cannot be null")
    private String password;

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

    // Getters and Setters...
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
