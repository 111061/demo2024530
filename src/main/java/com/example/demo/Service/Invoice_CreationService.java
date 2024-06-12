package com.example.demo.Service;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.DTO.Invoice_CreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.lang.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.util.List;

@Service
public class Invoice_CreationService {

    private static final Logger logger = LoggerFactory.getLogger(Invoice_CreationService.class);

    @Autowired
    private Invoice_CreationRepository invoiceCreationRepository;

    public byte[] exportInvoiceById(Long id) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Invoice_Creation invoiceCreation = invoiceCreationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));

            // Calculate and update settlement value
            double settlementValue = calculateSettlement(id);
            invoiceCreation.setSettlementValue(settlementValue);
            double unit_price = invoiceCreation.getUnitPrice();
            double total = settlementValue+unit_price;


            String templateFilePath = "C:\\Users\\rober\\IdeaProjects\\demo20240603\\請求書テンプレート.xlsx";
            FileInputStream fis = new FileInputStream(templateFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            // Update specific cells with invoiceCreation data
            Row row = sheet.getRow(22);
            row.getCell(1).setCellValue(invoiceCreation.getOrderNumber()); // B23
            row.getCell(2).setCellValue(invoiceCreation.getEngineer()); // C23
            row.getCell(3).setCellValue(invoiceCreation.getProjectName()); // D23
            row.getCell(5).setCellValue(invoiceCreation.getUnitPrice()); // F23
            row.getCell(6).setCellValue(invoiceCreation.getSettlementUpperLimit()); // G23
            row.getCell(7).setCellValue(invoiceCreation.getSettlementLowerLimit()); // H23
            row.getCell(8).setCellValue(invoiceCreation.getDeductionUnitPriceTotal()); // I23
            row.getCell(9).setCellValue(invoiceCreation.getOvertimeUnitPrice()); // J23
            row.getCell(10).setCellValue(invoiceCreation.getWorkTime()); // K23
            row.getCell(11).setCellValue(invoiceCreation.getSettlementValue()); // L23
            row.getCell(12).setCellValue(total); // M23
            double check_settlement = invoiceCreation.getSettlementValue();
            logger.info("Checking settlement value: {}", check_settlement);


            // Write changes to the output stream
            workbook.write(byteArrayOutputStream);
            workbook.close();
        } catch (IOException e) {
            logger.error("Error generating Excel for invoice with ID: " + id, e);
        }
        return byteArrayOutputStream.toByteArray();
    }



    public ByteArrayInputStream exportInvoicesToExcel() {
        List<Invoice_Creation> invoices = invoiceCreationRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Invoices");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("注文番号");
            headerRow.createCell(1).setCellValue("作業担当");
            headerRow.createCell(2).setCellValue("プロジェクト名");
            headerRow.createCell(3).setCellValue("単価（円）");
            headerRow.createCell(4).setCellValue("精算上限");
            headerRow.createCell(5).setCellValue("精算下限");
            headerRow.createCell(6).setCellValue("控除単価");
            headerRow.createCell(7).setCellValue("超過単価");
            headerRow.createCell(8).setCellValue("出勤時間");
            headerRow.createCell(9).setCellValue("精算金額");
            headerRow.createCell(10).setCellValue("小計(円)");



            // Add other headers...

            int rowIdx = 1;
            for (Invoice_Creation invoice : invoices) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(invoice.getOrderNumber());
                row.createCell(1).setCellValue(invoice.getEngineer());
                row.createCell(2).setCellValue(invoice.getProjectName());
                row.createCell(3).setCellValue(invoice.getUnitPrice());
                row.createCell(4).setCellValue(invoice.getSettlementUpperLimit());
                row.createCell(5).setCellValue(invoice.getSettlementLowerLimit());
                row.createCell(6).setCellValue(invoice.getDeductionUnitPriceTotal());
                row.createCell(7).setCellValue(invoice.getOvertimeUnitPrice());
                row.createCell(8).setCellValue(invoice.getWorkTime());
                row.createCell(9).setCellValue(invoice.getSettlementValue());


                //row.createCell(10).setCellValue(invoice.get());


                // Add other fields...
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export invoices to Excel file", e);
        }
    }



    @Autowired
    public Invoice_CreationService(Invoice_CreationRepository invoiceCreationRepository) {
        this.invoiceCreationRepository = invoiceCreationRepository;
    }

    public double calculateSettlement(Long id) {
        Invoice_Creation invoiceCreation = invoiceCreationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice_Creation not found for this id :: " + id));

        double workTime = invoiceCreation.getWorkTime();
        double settlementLowerLimit = invoiceCreation.getSettlementLowerLimit();
        double settlementUpperLimit = invoiceCreation.getSettlementUpperLimit();
        double overtimeUnitPrice = invoiceCreation.getOvertimeUnitPrice();
        double deductionUnitPriceTotal = invoiceCreation.getDeductionUnitPriceTotal();

        // 计算公式
        logger.info("Calculating settlement value for Invoice ID: {}", id);
        logger.info("WorkTime: {}, SettlementLowerLimit: {}, SettlementUpperLimit: {}, OvertimeUnitPrice: {}, DeductionUnitPriceTotal: {}",
                workTime, settlementLowerLimit, settlementUpperLimit, overtimeUnitPrice, deductionUnitPriceTotal);

        double settlementValue = 0;
        if (workTime < settlementLowerLimit) {
            settlementValue = (settlementLowerLimit - workTime) * deductionUnitPriceTotal;
        } else if (workTime > settlementUpperLimit) {
            settlementValue = (workTime - settlementUpperLimit) * overtimeUnitPrice;
        }

        logger.info("Calculated Settlement Value: {}", settlementValue);
        return settlementValue;
    }




    public List<Invoice_Creation> findAllInvoiceCreation() {
        return invoiceCreationRepository.findAll();
    }

    public List<Invoice_Creation> findByparentCompany(String parentCompany) {
        return invoiceCreationRepository.findByparentCompany(parentCompany);
    }

    public Invoice_Creation findInvoiceCreationById(Long id) {
        return invoiceCreationRepository.findById(id).orElse(null);
    }

    public List<Invoice_Creation> findByorderNumber(String orderNumber) {
        return invoiceCreationRepository.findByorderNumber(orderNumber);
    }




    public Invoice_Creation addInvoiceCreation(Invoice_Creation invoiceCreation) {
        return invoiceCreationRepository.save(invoiceCreation);
    }

    public void deleteInvoiceCreateById(Long id) {
        invoiceCreationRepository.deleteById(id);
    }

    public List<Invoice_Creation> searchInvoice_creations(String parentCompany, String orderNumber, String engineer, String keyword) {
        if ("ALL".equals(parentCompany)) parentCompany = null;
        if ("ALL".equals(orderNumber)) orderNumber = null;
        if ("ALL".equals(engineer)) engineer = null;

        return invoiceCreationRepository.searchInvoice_creations(parentCompany, orderNumber, engineer, keyword);
    }

    // 其他查詢方法...


}
