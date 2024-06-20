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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EstimateService {
    private static final Logger logger = LoggerFactory.getLogger(EstimateService.class);

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

            // 设定要插入数据的起始行号和列号
            int startRowNum = 23;  // 这里的23代表Excel中的第24行（因为索引从0开始）
            int startColNum = 2;   // 这里的2代表Excel中的第C列

            // Fetch estimates and populate the rows
            List<Estimate> estimates = findEstimatesByIds(estimateIds);

            // 获取模板行的单元格样式
            Row templateRow = sheet.getRow(startRowNum - 1);
            CellStyle templateStyle = null;
            if (templateRow != null) {
                Cell templateCell = templateRow.getCell(startColNum);
                if (templateCell != null) {
                    templateStyle = templateCell.getCellStyle();
                }
            }

            for (Estimate estimate : estimates) {
                Row row = sheet.getRow(startRowNum);
                if (row == null) {
                    row = sheet.createRow(startRowNum);
                }
                createCellWithStyle(row, startColNum, estimate.getOperatorName(), templateStyle);
                createCellWithStyle(row, startColNum + 1, estimate.getTaskDescription(), templateStyle);
                String period = estimate.getTaskPeriodStart().toString() + " - " + estimate.getTaskPeriodEnd().toString();
                createCellWithStyle(row, startColNum + 2, period, templateStyle);
                createCellWithStyle(row, startColNum + 3, estimate.getUnitPrice().doubleValue(), templateStyle);
                createCellWithStyle(row, startColNum + 4, estimate.getQuantity().doubleValue(), templateStyle);
                createCellWithStyle(row, startColNum + 5, estimate.getSubtotal().doubleValue(), templateStyle);
                startRowNum++;  // 每次循环后移到下一行
            }

            // 在 F14 单元格中插入指定的字符串
            Row rowF14 = sheet.getRow(13); // F14对应的行号为13（从0开始计数）
            if (rowF14 == null) {
                rowF14 = sheet.createRow(13);
            }
            Cell cellF14 = rowF14.getCell(5); // F列对应的列号为5（从0开始计数）
            if (cellF14 == null) {
                cellF14 = rowF14.createCell(5);
            }
            cellF14.setCellValue("　　　　担当者：" + estimates.get(0).getResponsiblePerson() + "　　　　承認者：" + estimates.get(0).getApprover());

            // 刷新公式
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateAll();

            // Write the updated workbook to the output file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            logger.error("Error exporting estimates to Excel", e);
            throw new IOException("Error exporting estimates to Excel", e);
        }
    }

    private void createCellWithStyle(Row row, int colIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(colIndex);
        if (style != null) {
            CellStyle newCellStyle = row.getSheet().getWorkbook().createCellStyle();
            newCellStyle.cloneStyleFrom(style);
            cell.setCellStyle(newCellStyle);

            // 明确设置边框样式
            newCellStyle.setBorderTop(BorderStyle.THIN);
            newCellStyle.setBorderBottom(BorderStyle.THIN);
            newCellStyle.setBorderLeft(BorderStyle.THIN);
            newCellStyle.setBorderRight(BorderStyle.THIN);
        }
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
