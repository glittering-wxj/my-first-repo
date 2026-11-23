package com.example.demo1.repository;

import com.example.demo1.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 购物车项数据访问层
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * 根据购物车ID查找购物车项
     */
    List<CartItem> findByCartId(Long cartId);

    /**
     * 根据购物车ID和商品ID查找购物车项
     */
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    /**
     * 根据购物车ID和选中状态查找购物车项
     */
    List<CartItem> findByCartIdAndChecked(Long cartId, Boolean checked);

    /**
     * 删除购物车的所有项
     */
    void deleteByCartId(Long cartId);
}
