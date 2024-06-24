package com.example.demo.DTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.DTO.Order;


import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 自定义查询方法
}
