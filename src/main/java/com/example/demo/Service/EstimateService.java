package com.example.demo.Service;

import com.example.demo.DTO.Estimate;
import com.example.demo.DTO.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.RoundingMode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
@Service
public class EstimateService {
    private final EstimateRepository estimateRepository;

    @Autowired
    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    public Estimate addEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);
    }

    public List<Estimate> addMultipleEstimates(Estimate estimate) {
        List<Estimate> estimates = new ArrayList<>();

        LocalDate startDate = estimate.getTaskPeriodStart();
        LocalDate endDate = estimate.getTaskPeriodEnd();
        int quantity = estimate.getQuantity();

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal monthsDecimal = new BigDecimal(totalDays).divide(new BigDecimal(30), 0, RoundingMode.HALF_UP);

        long totalMonths = monthsDecimal.longValue();
        if (totalMonths == 0) {
            totalMonths = 1;
        }

        long periodMonths = totalMonths / quantity;

        if (periodMonths > 7) {
            throw new IllegalArgumentException("Period months exceed the limit of 7.");
        }

        LocalDate currentStartDate = startDate;
        for (int i = 0; i < periodMonths; i++) {
            LocalDate currentEndDate = currentStartDate.plusMonths(quantity).minusDays(1);
            if (currentEndDate.isAfter(endDate)) {
                currentEndDate = endDate;
            }

            Estimate newEstimate = new Estimate();
            newEstimate.setEstimateNumber(estimate.getEstimateNumber());
            newEstimate.setOperatorName(estimate.getOperatorName());
            newEstimate.setTaskDescription(estimate.getTaskDescription());
            newEstimate.setTaskPeriodStart(currentStartDate);
            newEstimate.setTaskPeriodEnd(currentEndDate);
            newEstimate.setUnitPrice(estimate.getUnitPrice());
            newEstimate.setQuantity(estimate.getQuantity());
            newEstimate.setSubtotal(estimate.getUnitPrice().multiply(BigDecimal.valueOf(estimate.getQuantity())));
            newEstimate.setResponsiblePerson(estimate.getResponsiblePerson());
            newEstimate.setApprover(estimate.getApprover());

            estimates.add(estimateRepository.save(newEstimate));
            currentStartDate = currentEndDate.plusDays(1);
        }

        return estimates;
    }

    public List<Estimate> findAllEstimates() {
        return estimateRepository.findAll();
    }

    public void deleteEstimatesByIds(List<Long> ids) {
        estimateRepository.deleteAllById(ids);
    }

    public Estimate updateEstimate(Long id, Estimate estimate) {
        Optional<Estimate> existingEstimate = estimateRepository.findById(id);
        if (existingEstimate.isPresent()) {
            Estimate updatedEstimate = existingEstimate.get();
            updatedEstimate.setEstimateNumber(estimate.getEstimateNumber());
            updatedEstimate.setOperatorName(estimate.getOperatorName());
            updatedEstimate.setTaskDescription(estimate.getTaskDescription());
            updatedEstimate.setTaskPeriodStart(estimate.getTaskPeriodStart());
            updatedEstimate.setTaskPeriodEnd(estimate.getTaskPeriodEnd());
            updatedEstimate.setUnitPrice(estimate.getUnitPrice());
            updatedEstimate.setQuantity(estimate.getQuantity());
            updatedEstimate.setSubtotal(estimate.getSubtotal());
            updatedEstimate.setResponsiblePerson(estimate.getResponsiblePerson());
            updatedEstimate.setApprover(estimate.getApprover());
            return estimateRepository.save(updatedEstimate);
        } else {
            return null;
        }
    }

    public List<Estimate> searchEstimates(String keyword) {
        return estimateRepository.searchByKeyword(keyword);
    }

    public List<Estimate> findEstimatesByIds(List<Long> ids) {
        return estimateRepository.findAllById(ids);
    }

    public void exportEstimatesToExcel(String inputFilePath, String outputFilePath, List<Long> estimateIds) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            // Fetch estimates and populate the rows
            List<Estimate> estimates = findEstimatesByIds(estimateIds);
            for (Estimate estimate : estimates) {
                Row row = sheet.createRow(++lastRowNum);
                row.createCell(2).setCellValue(estimate.getOperatorName());
                row.createCell(3).setCellValue(estimate.getTaskDescription());
                row.createCell(4).setCellValue(estimate.getResponsiblePerson());
                row.createCell(5).setCellValue(estimate.getApprover());
                row.createCell(6).setCellValue(estimate.getTaskPeriodStart().toString());
                row.createCell(7).setCellValue(estimate.getTaskPeriodEnd().toString());
                row.createCell(8).setCellValue(estimate.getUnitPrice().toString());
                row.createCell(9).setCellValue(estimate.getQuantity());
                row.createCell(10).setCellValue(estimate.getSubtotal().toString());
            }

            // Write the updated workbook to the output file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                workbook.write(fos);
            }
        }
    }
}
