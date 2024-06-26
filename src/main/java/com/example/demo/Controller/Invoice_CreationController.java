package com.example.demo.Controller;

import org.springframework.core.io.InputStreamResource;
import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.Invoice_CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ContentDisposition;



import java.io.ByteArrayInputStream;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/invoicecreation")
public class Invoice_CreationController {

    private static final Logger logger = LoggerFactory.getLogger(Invoice_CreationController.class);

    @Autowired
    private final Invoice_CreationService invoiceCreationService;
    private final EmailService emailService;

    @Autowired
    public Invoice_CreationController(Invoice_CreationService invoiceCreationService, EmailService emailService) {
        this.invoiceCreationService = invoiceCreationService;
        this.emailService = emailService;
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<?> exportInvoiceCreation(@PathVariable Long id) {
        try {
            logger.info("Exporting invoice with ID: " + id);
            byte[] data = invoiceCreationService.exportInvoiceById(id);
            Invoice_Creation invoice = invoiceCreationService.findInvoiceCreationById(id);
            String engineer = invoice.getEngineer();
            String fileName = engineer + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error exporting invoice with ID: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
            logger.error("Error retrieving invoice creations: ", e);
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
            logger.error("Error retrieving invoice creations by parent company: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Invoice_Creation> addInvoiceCreation(@RequestBody Invoice_Creation invoiceCreation) {
        try {
            Invoice_Creation savedInvoiceCreation = invoiceCreationService.addInvoiceCreation(invoiceCreation);
            return new ResponseEntity<>(savedInvoiceCreation, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding invoice creation: ", e);
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
            logger.error("Error deleting invoice creations: ", e);
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
            List<Invoice_Creation> invoiceCreations = invoiceCreationService.searchInvoice_creations(parentCompany, orderNumber, engineer, keyword);
            for (Invoice_Creation invoice : invoiceCreations) {
                double settlementValue = invoiceCreationService.calculateSettlement(invoice.getId());
                invoice.setSettlementValue(settlementValue);
            }
            if (invoiceCreations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(invoiceCreations);
        } catch (Exception e) {
            logger.error("Error searching invoice creations: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 添加一個更新契約的API
    @PutMapping("/update/{id}")
    public ResponseEntity<Invoice_Creation> updateInvoice_Creation(@PathVariable Long id, @RequestBody Invoice_Creation invoiceCreation) {
        try {
            Invoice_Creation existingInvoice_Creation = invoiceCreationService.findInvoiceCreationById(id);
            if (existingInvoice_Creation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 更新契約資料
            existingInvoice_Creation.setParentCompany(invoiceCreation.getParentCompany());
            existingInvoice_Creation.setOrderNumber(invoiceCreation.getOrderNumber());
            existingInvoice_Creation.setEngineer(invoiceCreation.getEngineer());
            existingInvoice_Creation.setWorkTime(invoiceCreation.getWorkTime());
            existingInvoice_Creation.setProjectName(invoiceCreation.getProjectName());
            existingInvoice_Creation.setParentSales(invoiceCreation.getParentSales());
            existingInvoice_Creation.setUnitPrice(invoiceCreation.getUnitPrice());
            existingInvoice_Creation.setPaymentTerms(invoiceCreation.getPaymentTerms());
            existingInvoice_Creation.setSettlement(invoiceCreation.getSettlement());
            existingInvoice_Creation.setSettlementLowerLimit(invoiceCreation.getSettlementLowerLimit());
            existingInvoice_Creation.setSettlementUpperLimit(invoiceCreation.getSettlementUpperLimit());
            existingInvoice_Creation.setOvertimeUnitPrice(invoiceCreation.getOvertimeUnitPrice());
            existingInvoice_Creation.setDeductionUnitPriceTotal(invoiceCreation.getDeductionUnitPriceTotal());
            existingInvoice_Creation.setSettlementTimeUnit(invoiceCreation.getSettlementTimeUnit());
            existingInvoice_Creation.setDailyRateSetting(invoiceCreation.getDailyRateSetting());
            existingInvoice_Creation.setEntryDate(invoiceCreation.getEntryDate());
            existingInvoice_Creation.setExpectedExitDate(invoiceCreation.getExpectedExitDate());

            Invoice_Creation updatedInvoice_Creation = invoiceCreationService.addInvoiceCreation(existingInvoice_Creation);
            return new ResponseEntity<>(updatedInvoice_Creation, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating invoice creation: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            logger.error("Error sending emails: ", e);
            return new ResponseEntity<>("Error sending emails", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
