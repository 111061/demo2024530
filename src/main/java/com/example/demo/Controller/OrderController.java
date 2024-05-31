package com.example.demo.Controller;

import com.example.demo.DTO.EmailDetails;
import com.example.demo.DTO.Order;
import com.example.demo.DTO.Partner;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public ResponseEntity<List<Order>> getAllPartners() {
        try {
            List<Order> orders = orderService.findAllOrders();
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build(); // 返回204 No Content状态
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回500 Internal Server Error状态
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.addPartner(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 输出错误信息到控制台
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Order>> deleteOrder(@RequestBody List<Long> orderIds){
        try {
            for (Long orderId : orderIds) {
                orderService.deleteOrderById(orderId);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    // 其他端点...
}
