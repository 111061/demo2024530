package com.example.demo.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.example.demo.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
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


    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public byte[] exportOrdersById(Long id) {
        List<Order> orders = orderRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Workbook workbook = null;
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));


            ClassPathResource resource = new ClassPathResource("template/注文書テンプレート.xlsx");
            try (InputStream fis = resource.getInputStream()) {
                workbook = new XSSFWorkbook(fis);
            }
            Sheet sheet = workbook.getSheetAt(0);

            // Update specific cells with invoiceCreation data
            Row row = sheet.getRow(28);
            row.getCell(1).setCellValue(order.getId()); // B29


            row.getCell(3).setCellValue(order.getJob_details()); // DE29
            row.getCell(5).setCellValue(order.getEngineer()); // F29
            row.getCell(6).setCellValue(order.getEntryDate()); // G29
            row.getCell(7).setCellValue(order.getExpectedExitDate()); // H29
            row.getCell(8).setCellValue(order.getPerson_month()); // I29
            row.getCell(9).setCellValue(order.getSettlementUpperLimit()); // J29
            row.getCell(10).setCellValue(order.getSettlementLowerLimit()); // K29
            row.getCell(11).setCellValue(order.getUnitPrice()); // L29
            row.getCell(12).setCellValue(roundUp(order.getUnitPrice() / order.getSettlementUpperLimit(), 0)); // M29
            row.getCell(13).setCellValue(roundUp(order.getUnitPrice() / order.getSettlementLowerLimit(), 0)); // N29
            row.getCell(14).setCellValue(order.getUnitPrice()*order.getPerson_month());//O29
            Row row30 = sheet.getRow(30);

            row30.getCell(14).setCellValue(order.getUnitPrice()*order.getPerson_month());//O31


            Row row8 = sheet.getRow(8);
            row8.getCell(1).setCellValue(order.getContractingCompany());//B9

            Row row21 = sheet.getRow(21);
            row21.getCell(1).setCellValue("件名："+order.getProjectName());//B22

            Row row22 = sheet.getRow(22);

            double total = order.getUnitPrice()*order.getPerson_month();
            int intTotal = (int) total;
            row22.getCell(1).setCellValue("金額（JPY税抜き）：￥"+intTotal);//B23


            Row row23 = sheet.getRow(23);
            row23.getCell(1).setCellValue("作業場所:　"+order.getWork_place()); //B24

            Row row24 = sheet.getRow(24);
            row24.getCell(1).setCellValue("支払条件:　"+order.getPaymentTerms());  //B25


            // Set current date in cell M12
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedDate = currentDate.format(formatter);
            Row row11 = sheet.getRow(13);
            row11.getCell(11).setCellValue("注文日：" + formattedDate); // L14


            // Write changes to the output stream
            workbook.write(byteArrayOutputStream);
            workbook.close();


        } catch (IOException e) {
            logger.error("Error generating Excel for invoice with ID: " + id, e);
        }
        return byteArrayOutputStream.toByteArray();

    }


    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order addPartner(Order order) {
        // 處理新增合作夥伴的邏輯
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

}
