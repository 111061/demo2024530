package com.example.demo.Service;

import com.example.demo.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
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
    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }

}
