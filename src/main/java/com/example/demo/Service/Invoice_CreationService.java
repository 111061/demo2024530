package com.example.demo.Service;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.DTO.Invoice_CreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // roundUp 方法
    public static double roundUp(double value, int places) {
        if (places < 0) {
            double scale = Math.pow(10, -places);
            return Math.ceil(value / scale) * scale;
        } else {
            double scale = Math.pow(10, places);
            return Math.ceil(value * scale) / scale;
        }
    }


    public byte[] exportInvoiceById(Long id) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Workbook workbook = null;
        try {
            Invoice_Creation invoiceCreation = invoiceCreationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));

            // Calculate and update settlement value
            double settlementValue = calculateSettlement(id);
            invoiceCreation.setSettlementValue(settlementValue);
            double unit_price = invoiceCreation.getUnitPrice();
            double total = settlementValue+unit_price;


            ClassPathResource resource = new ClassPathResource("template/請求書テンプレート.xlsx");
            try (InputStream fis = resource.getInputStream()) {
                workbook = new XSSFWorkbook(fis);
            }
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

            Row row27 = sheet.getRow(27);
            row27.getCell(12).setCellValue(total);//M28


            Row row19 = sheet.getRow(19);
            row19.getCell(2).setCellValue(total+roundUp((total*0.1),0));//C20

            Row row28 = sheet.getRow(28);
            row28.getCell(12).setCellValue(roundUp((total*0.1),0));//M29

            Row row8 = sheet.getRow(8);
            row8.getCell(1).setCellValue(invoiceCreation.getParentCompany());//B9

            Row row29 = sheet.getRow(29);
            row29.getCell(12).setCellValue(total+roundUp((total*0.1),0));//M30

            // Set current date in cell M12
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedDate = currentDate.format(formatter);
            Row row11 = sheet.getRow(11);
            row11.getCell(12).setCellValue(formattedDate); // M12



            // Write changes to the output stream
            workbook.write(byteArrayOutputStream);
            workbook.close();
        } catch (IOException e) {
            logger.error("Error generating Excel for invoice with ID: " + id, e);
        }
        return byteArrayOutputStream.toByteArray();
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
