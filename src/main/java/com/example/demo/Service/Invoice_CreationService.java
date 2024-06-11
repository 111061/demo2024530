package com.example.demo.Service;

import com.example.demo.DTO.Contract_Management_Screen;
import com.example.demo.DTO.Invoice_Creation;
import com.example.demo.DTO.Invoice_CreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.util.List;

@Service
public class Invoice_CreationService {

    private final Invoice_CreationRepository invoiceCreationRepository;

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
        double settlementValue = 0.0;
        if (workTime < settlementLowerLimit) {
            settlementValue = (settlementLowerLimit - workTime) * deductionUnitPriceTotal;
        } else if (workTime > settlementUpperLimit) {
            settlementValue = (workTime - settlementUpperLimit) * overtimeUnitPrice;
        }

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
