package com.example.demo.Controller;

import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.Invoice_CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoicecreation")
public class Invoice_CreationController {

    private final Invoice_CreationService invoiceCreationService;
    private final EmailService emailService;

    @Autowired
    public Invoice_CreationController(Invoice_CreationService invoiceCreationService, EmailService emailService) {
        this.invoiceCreationService = invoiceCreationService;
        this.emailService = emailService;
    }

    @GetMapping("/test")
    public ResponseEntity<List<Invoice_Creation>> getAllInvoiceCreation() {
        try {
            List<Invoice_Creation> invoiceCreations = invoiceCreationService.findAllInvoiceCreation();
            if (invoiceCreations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(invoiceCreations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Invoice_Creation> addInvoiceCreation(@RequestBody Invoice_Creation invoiceCreation) {
        try {
            Invoice_Creation savedInvoiceCreation = invoiceCreationService.addInvoiceCreation(invoiceCreation);
            return new ResponseEntity<>(savedInvoiceCreation, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInvoiceCreate(@RequestBody List<Long> invoiceCreateIds) {
        try {
            for (Long invoiceCreateId : invoiceCreateIds) {
                invoiceCreationService.deleteInvoiceCreateById(invoiceCreateId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sendEmails")
    public ResponseEntity<String> sendEmailsToInvoiceCreate(@RequestBody EmailDetails details) {
        try {
            String subject = details.getSubject();
            String content = details.getContent();
            String account = details.getAccount();
            String password = details.getPassword();
            emailService.sendEmail(details.getEmails(), subject, content, account, password);
            return new ResponseEntity<>("Emails sent successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error sending emails", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

