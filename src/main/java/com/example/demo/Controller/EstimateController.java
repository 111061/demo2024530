package com.example.demo.Controller;

import com.example.demo.DTO.Estimate;
import com.example.demo.Service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/estimates")
public class EstimateController {
    private final EstimateService estimateService;

    @Autowired
    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
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
    public ResponseEntity<String> exportToExcel(@RequestBody List<Long> estimateIds) {
        String inputFilePath = "C:\\Users\\www\\IdeaProjects\\demo\\見積書.xlsx";
        String outputFilePath = "C:\\Users\\www\\IdeaProjects\\demo\\新見積書.xlsx";

        try {
            estimateService.exportEstimatesToExcel(inputFilePath, outputFilePath, estimateIds);
            return ResponseEntity.ok("导出成功！");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("导出失败: " + e.getMessage());
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
