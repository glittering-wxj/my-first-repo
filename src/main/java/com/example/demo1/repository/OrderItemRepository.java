package com.example.demo1.repository;

import com.example.demo1.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单项数据访问层
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * 根据订单ID查找订单项
     */
    List<OrderItem> findByOrderId(Long orderId);
}
