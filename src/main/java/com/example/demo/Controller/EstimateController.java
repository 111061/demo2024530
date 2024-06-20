package com.example.demo.Controller;

import com.example.demo.DTO.Estimate;
import com.example.demo.Service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.File;
import java.nio.file.Files;
import com.example.demo.DTO.EstimateRepository;
import java.util.stream.Collectors;
import java.nio.file.Paths;
@RestController
@RequestMapping("/api/estimates")
public class EstimateController {
    private final EstimateService estimateService;

    @Autowired
    private EstimateRepository estimateRepository;
    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }
    @GetMapping("/numbers")
    public List<String> getAllEstimateNumbers() {
        return estimateRepository.findAll().stream()
                .map(Estimate::getEstimateNumber)
                .distinct() // 去重
                .collect(Collectors.toList());
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<List<Estimate>> addMultipleEstimates(@RequestBody Estimate estimate) {
        try {
            List<Estimate> savedEstimates = estimateService.addMultipleEstimates(estimate);
            return ResponseEntity.ok(savedEstimates);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Estimate>> searchEstimates(@RequestParam(required = false, defaultValue = "") String keyword) {
        try {
            List<Estimate> estimates = estimateService.searchEstimates(keyword);
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(@RequestBody ExportRequest exportRequest) {
        String estimateNumber = exportRequest.getEstimateNumber();
        List<Long> estimateIds = exportRequest.getEstimateIds();

        // 获取项目的根目录
        String projectRoot = System.getProperty("user.dir");

        // 构建相对路径
        String inputFilePath = Paths.get(projectRoot, "src", "main", "resources", "template", "見積書.xlsx").toString();
        String outputFilePath = Paths.get(projectRoot, "src", "main", "resources", "template", "Estimate", estimateNumber + ".xlsx").toString();

        System.out.print(inputFilePath+"..."+outputFilePath);

        try {
            estimateService.exportEstimatesToExcel(inputFilePath, outputFilePath, estimateIds);

            // Read the output file and return as a byte array
            File file = new File(outputFilePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", estimateNumber + ".xlsx");

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Define a class to handle the request payload
    public static class ExportRequest {
        private List<Long> estimateIds;
        private String estimateNumber;

        // Getters and Setters
        public List<Long> getEstimateIds() {
            return estimateIds;
        }

        public void setEstimateIds(List<Long> estimateIds) {
            this.estimateIds = estimateIds;
        }

        public String getEstimateNumber() {
            return estimateNumber;
        }

        public void setEstimateNumber(String estimateNumber) {
            this.estimateNumber = estimateNumber;
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEstimates(@RequestBody List<Long> ids) {
        try {
            estimateService.deleteEstimatesByIds(ids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Estimate> updateEstimate(@PathVariable Long id, @RequestBody Estimate estimate) {
        try {
            Estimate updatedEstimate = estimateService.updateEstimate(id, estimate);
            if (updatedEstimate != null) {
                return ResponseEntity.ok(updatedEstimate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 添加服务以根据ID列表查找估算
    @GetMapping("/findByIds")
    public List<Estimate> findEstimatesByIds(@RequestBody List<Long> ids) {
        return estimateService.findEstimatesByIds(ids);
    }
}
