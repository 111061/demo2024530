package com.example.demo.Controller;
import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Employee;
import com.example.demo.DTO.Partner;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.Service.Invoice_CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.example.demo.Service.EmailService;
import com.example.demo.DTO.EmailDetails;



@RestController
@RequestMapping("/api/invoice_creation")
public class Invoice_CreationController {
    private final Invoice_CreationService invoiceCreationService;

    @Autowired
    public Invoice_CreationController(Invoice_CreationService invoiceCreationService) {
        this.invoiceCreationService = invoiceCreationService;
    }
    @Autowired
    private EmailService emailService;

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
    @PostMapping("/add")
    public ResponseEntity<Invoice_Creation> addPartner(@RequestBody Invoice_Creation invoiceCreation) {
        try {
            Invoice_Creation savedInvoiceCreation = invoiceCreationService.addInvoiceCreation(invoiceCreation);
            return new ResponseEntity<>(savedInvoiceCreation, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 输出错误信息到控制台
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Invoice_Creation>> deleteInvoiceCreate(@RequestBody List<Long> invoiceCreateIds){
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
    @PostMapping("/sendEmails")
    public ResponseEntity<?> sendEmailsToInvoiceCreate(@RequestBody EmailDetails details) {
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
