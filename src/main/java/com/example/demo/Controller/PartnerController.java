package com.example.demo.Controller;

import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Partner;
import com.example.demo.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Service.EmailService;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public ResponseEntity<List<Partner>> getAllPartners() {
        try {
            List<Partner> partners = partnerService.findAllPartners();
            if (partners.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(partners);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Partner> addPartner(@RequestBody Partner partner) {
        try {
            Partner savedPartner = partnerService.addPartner(partner);
            return new ResponseEntity<>(savedPartner, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePartner(@RequestBody List<Long> partnerIds) {
        try {
            for (Long partnerId : partnerIds) {
                partnerService.deletePartnerById(partnerId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        try {
            Partner existingPartner = partnerService.findPartnerById(id);
            if (existingPartner == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            existingPartner.setCompany(partner.getCompany());
            existingPartner.setSalesRep(partner.getSalesRep());
            existingPartner.setMail(partner.getMail());

            Partner updatedPartner = partnerService.addPartner(existingPartner);
            return new ResponseEntity<>(updatedPartner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendEmails")
    public ResponseEntity<?> sendEmailsToPartner(@RequestBody EmailDetails details) {
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
