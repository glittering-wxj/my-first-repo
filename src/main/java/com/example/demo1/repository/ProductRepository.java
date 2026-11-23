package com.example.demo1.repository;

import com.example.demo1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品数据访问层
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 根据分类ID查找商品
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * 根据分类ID和状态查找商品
     */
    List<Product> findByCategoryIdAndStatus(Long categoryId, Integer status);

    /**
     * 根据商品名称模糊查询
     */
    List<Product> findByNameContaining(String name);

    /**
     * 根据状态查找商品
     */
    List<Product> findByStatus(Integer status);

    /**
     * 根据商品名称和状态模糊查询
     */
    List<Product> findByNameContainingAndStatus(String name, Integer status);
}
