package com.example.demo.Controller;

import com.example.demo.DTO.*;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final EmailService emailService;

    @Autowired
    public OrderController(OrderService orderService, EmailService emailService) {
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<?> exportOrder(@PathVariable Long id) {
        try {

            byte[] data = orderService.exportOrdersById(id);
            Order order = orderService.findOrderById(id);
            String engineer = order.getEngineer();
            String fileName = engineer + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error exporting order with ID: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] toByteArray(ByteArrayInputStream input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @GetMapping("/test")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findAllOrders();
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build(); // 返回204 No Content状态
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error retrieving orders: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回500 Internal Server Error状态
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.addOrder(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 输出错误信息到控制台
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOrder(@RequestBody List<Long> orderIds) {
        try {
            for (Long orderId : orderIds) {
                orderService.deleteOrderById(orderId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting orders: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            Order existingOrder = orderService.findOrderById(id);
            if (existingOrder == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 更新契約資料
            existingOrder.setSubcontractor(order.getSubcontractor());
            existingOrder.setEngineer(order.getEngineer());
            existingOrder.setContractingCompany(order.getContractingCompany());
            existingOrder.setProjectName(order.getProjectName());
            existingOrder.setSubcontractorSales(order.getSubcontractorSales());
            existingOrder.setUnitPrice(order.getUnitPrice());
            existingOrder.setPaymentTerms(order.getPaymentTerms());
            existingOrder.setSettlement(order.getSettlement());
            existingOrder.setSettlementLowerLimit(order.getSettlementLowerLimit());
            existingOrder.setSettlementUpperLimit(order.getSettlementUpperLimit());
            existingOrder.setOvertimeUnitPrice(order.getOvertimeUnitPrice());
            existingOrder.setDeductionUnitPrice(order.getDeductionUnitPrice());
            existingOrder.setSettlementTimeUnit(order.getSettlementTimeUnit());
            existingOrder.setDailyRateSetting(order.getDailyRateSetting());
            existingOrder.setEntryDate(order.getEntryDate());
            existingOrder.setExpectedExitDate(order.getExpectedExitDate());

            Order updatedOrder = orderService.addOrder(existingOrder);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating order: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendEmails")
    public ResponseEntity<?> sendEmailsToPartner(@RequestBody EmailDetails details) {
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
