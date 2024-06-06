package com.example.demo.Controller;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.Invoice_CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/invoicecreation")
public class Invoice_CreationController {

    private static final Logger logger = LoggerFactory.getLogger(Invoice_CreationController.class);


    private final Invoice_CreationService invoiceCreationService;
    private final EmailService emailService;

    @Autowired
    public Invoice_CreationController(Invoice_CreationService invoiceCreationService, EmailService emailService) {
        this.invoiceCreationService = invoiceCreationService;
        this.emailService = emailService;
    }




    @PostMapping("/calculate_settlement/{id}")
    public ResponseEntity<Double> calculateSettlement(@PathVariable Long id) {
        logger.info("Received request to calculate settlement for invoice creation with id: {}", id);
        try {
            double settlement = invoiceCreationService.calculateSettlement(id);
            logger.info("Calculated settlement value: {}", settlement);
            return ResponseEntity.ok(settlement);
        } catch (Exception e) {
            logger.error("Error calculating settlement: ", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<List<Invoice_Creation>> getAllInvoiceCreation() {
        try {
            List<Invoice_Creation> invoiceCreations = invoiceCreationService.findAllInvoiceCreation();
            if (invoiceCreations.isEmpty()) {
                return ResponseEntity.noContent().build(); // 返回204 No Content状态
            }
            return ResponseEntity.ok(invoiceCreations);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回500 Internal Server Error状态
        }
    }

    // 根据名义公司查询請求書
    @GetMapping("/test/parentCompany/{parentCompany}")
    public ResponseEntity<List<Invoice_Creation>> getInvoice_CreationByparentCompany(@PathVariable String parentCompany) {
        try {
            List<Invoice_Creation> invoice_creations = invoiceCreationService.findByparentCompany(parentCompany);
            if (invoice_creations.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(invoice_creations);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/add")
    public ResponseEntity<Invoice_Creation> addInvoiceCreation(@RequestBody Invoice_Creation invoiceCreation) {
        try {
            Invoice_Creation savedInvoiceCreation = invoiceCreationService.addInvoiceCreation(invoiceCreation);
            return new ResponseEntity<>(savedInvoiceCreation, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 输出错误信息到控制台
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
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // 搜索契约
    @GetMapping("/search")
    public ResponseEntity<List<Invoice_Creation>> searchInvoice_Creations(
            @RequestParam(required = false) String parentCompany,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String engineer,
            @RequestParam(required = false) String keyword) {
        try {
            List<Invoice_Creation> invoice_creations = invoiceCreationService.searchInvoice_creations(parentCompany, orderNumber, engineer, keyword);
            if (invoice_creations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(invoice_creations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @PostMapping("/sendEmails")
    public ResponseEntity<String> sendEmailsToInvoiceCreate(@RequestBody EmailDetails details) {
        try {
            // 获取邮件详情
            String subject = details.getSubject();
            String content = details.getContent();
            String account = details.getAccount();
            String password = details.getPassword();

            // 发送邮件到所有收件人
            emailService.sendEmail(details.getEmails(), subject, content, account, password);

            return new ResponseEntity<>("Emails sent successfully!", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); // 增加错误输出
            return new ResponseEntity<>("Error sending emails", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
