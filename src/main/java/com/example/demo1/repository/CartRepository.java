package com.example.demo1.repository;

import com.example.demo1.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 购物车数据访问层
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * 根据用户ID查找购物车
     */
    Optional<Cart> findByUserId(Long userId);
}
