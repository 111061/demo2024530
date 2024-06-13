package com.example.demo.Controller;

import com.example.demo.DTO.Estimate;
import com.example.demo.Service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(savedEstimates, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Estimate>> getAllEstimates() {
        try {
            List<Estimate> estimates = estimateService.findAllEstimates();
            if (estimates.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Estimate>> searchEstimates(@RequestParam(required = false, defaultValue = "") String keyword) {
        try {
            List<Estimate> estimates = estimateService.searchEstimates(keyword);
            if (estimates.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEstimates(@RequestBody List<Long> ids) {
        try {
            estimateService.deleteEstimatesByIds(ids);
            return new ResponseEntity<>(HttpStatus.OK);
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
}
