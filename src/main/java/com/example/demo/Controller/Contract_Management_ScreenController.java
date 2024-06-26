package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.Service.Contract_Management_ScreenService;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.example.demo.Service.EmailService;
import com.example.demo.DTO.EmailDetails;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/contracts")
public class Contract_Management_ScreenController {
    @Autowired
    private Contract_Management_ScreenService contractService;

    @Autowired
    private EmailService emailService;

    // 获取所有契约
    @GetMapping("/test")
    public ResponseEntity<List<Contract_Management_Screen>> getAllContracts() {
        try {
            List<Contract_Management_Screen> contracts = contractService.findAllContracts();
            if (contracts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(contracts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根据名义公司查询契约
    @GetMapping("/test/contractingCompany/{contractingCompany}")
    public ResponseEntity<List<Contract_Management_Screen>> getContractByContractingCompany(@PathVariable String contractingCompany) {
        try {
            List<Contract_Management_Screen> contracts = contractService.findByContractingCompany(contractingCompany);
            if (contracts.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(contracts);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 添加一个新契约
    @PostMapping("/add")
    public ResponseEntity<Contract_Management_Screen> addContract(@RequestBody Contract_Management_Screen contract) {
        try {
            Contract_Management_Screen savedContract = contractService.addContract(contract);
            return new ResponseEntity<>(savedContract, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 增加錯誤輸出
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 批量删除契约
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteContract(@RequestBody List<Long> contractIds) {
        try {
            for (Long contractId : contractIds) {
                contractService.deleteContractById(contractId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 搜索契约
    @GetMapping("/search")
    public ResponseEntity<List<Contract_Management_Screen>> searchContracts(
            @RequestParam(required = false) String contractingCompany,
            @RequestParam(required = false) String ourPosition,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) String keyword) {
        try {
            List<Contract_Management_Screen> contracts = contractService.searchContracts(contractingCompany, ourPosition, contractType, keyword);
            if (contracts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(contracts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // 添加一個更新契約的API
    @PutMapping("/update/{id}")
    public ResponseEntity<Contract_Management_Screen> updateContract(@PathVariable Long id, @RequestBody Contract_Management_Screen contract) {
        try {
            Contract_Management_Screen existingContract = contractService.findContractById(id);
            if (existingContract == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 更新契約資料
            existingContract.setContractingCompany(contract.getContractingCompany());
            existingContract.setContractedCompany(contract.getContractedCompany());
            existingContract.setContractType(contract.getContractType());
            existingContract.setOurPosition(contract.getOurPosition());
            existingContract.setOurSalesRepresentative(contract.getOurSalesRepresentative());
            existingContract.setTheirSalesRepresentative(contract.getTheirSalesRepresentative());
            existingContract.setContractDate(contract.getContractDate());
            existingContract.setQuotation(contract.getQuotation());

            Contract_Management_Screen updatedContract = contractService.addContract(existingContract);
            return new ResponseEntity<>(updatedContract, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //xsl連接
    String projectRoot = System.getProperty("user.dir");

    private final Path fileStorageLocation = Paths.get(projectRoot, "src", "main", "resources", "template", "Estimate").toAbsolutePath().normalize();

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 群发邮件
    @PostMapping("/sendEmails")
    public ResponseEntity<String> sendEmailsToContracts(@RequestBody EmailDetails details) {
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
