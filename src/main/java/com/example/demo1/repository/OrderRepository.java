package com.example.demo1.repository;

import com.example.demo1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单数据访问层
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 根据用户ID查找订单
     */
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 根据订单号查找订单
     */
    Optional<Order> findByOrderNo(String orderNo);

    /**
     * 根据用户ID和订单状态查找订单
     */
    List<Order> findByUserIdAndStatusOrderByCreateTimeDesc(Long userId, Integer status);

    /**
     * 查找所有订单，按创建时间倒序
     */
    List<Order> findAllByOrderByCreateTimeDesc();
}
