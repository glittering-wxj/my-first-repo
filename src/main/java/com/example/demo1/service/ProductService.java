package com.example.demo1.service;

import com.example.demo1.entity.Product;
import com.example.demo1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务类
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 获取所有商品
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 获取所有上架商品
     */
    public List<Product> findAllOnSale() {
        return productRepository.findByStatus(1);
    }

    /**
     * 根据分类ID获取商品（仅上架商品）
     */
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryIdAndStatus(categoryId, 1);
    }

    /**
     * 根据分类ID获取所有商品（包括下架商品，用于管理员）
     */
    public List<Product> findAllByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * 根据名称搜索商品
     */
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingAndStatus(name, 1);
    }

    /**
     * 根据ID查找商品
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
    }

    /**
     * 创建商品
     */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * 更新商品
     */
    public Product update(Product product) {
        Product existProduct = findById(product.getId());

        if (product.getName() != null) {
            existProduct.setName(product.getName());
        }
        if (product.getDescription() != null) {
            existProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            existProduct.setPrice(product.getPrice());
        }
        if (product.getStock() != null) {
            existProduct.setStock(product.getStock());
        }
        if (product.getImageUrl() != null) {
            existProduct.setImageUrl(product.getImageUrl());
        }
        if (product.getCategoryId() != null) {
            existProduct.setCategoryId(product.getCategoryId());
        }
        if (product.getStatus() != null) {
            existProduct.setStatus(product.getStatus());
        }

        return productRepository.save(existProduct);
    }

    /**
     * 删除商品
     */
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 上架商品
     */
    public void onSale(Long id) {
        Product product = findById(id);
        product.setStatus(1);
        productRepository.save(product);
    }

    /**
     * 下架商品
     */
    public void offSale(Long id) {
        Product product = findById(id);
        product.setStatus(0);
        productRepository.save(product);
    }

    /**
     * 更新库存
     */
    public void updateStock(Long id, Integer stock) {
        Product product = findById(id);
        product.setStock(stock);
        productRepository.save(product);
    }

    /**
     * 减少库存
     */
    public void decreaseStock(Long id, Integer quantity) {
        Product product = findById(id);
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}
