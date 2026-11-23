package com.example.demo1.controller;

import com.example.demo1.entity.Category;
import com.example.demo1.entity.Product;
import com.example.demo1.service.CategoryService;
import com.example.demo1.service.ProductService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器（用户端）
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     */
    @GetMapping("/categories")
    public Result<List<Category>> getCategories() {
        try {
            List<Category> categories = categoryService.findAll();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取顶级分类
     */
    @GetMapping("/categories/top")
    public Result<List<Category>> getTopCategories() {
        try {
            List<Category> categories = categoryService.findTopCategories();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取子分类
     */
    @GetMapping("/categories/{parentId}/children")
    public Result<List<Category>> getChildCategories(@PathVariable Long parentId) {
        try {
            List<Category> categories = categoryService.findByParentId(parentId);
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取所有上架商品
     */
    @GetMapping("/list")
    public Result<List<Product>> getProducts() {
        try {
            List<Product> products = productService.findAllOnSale();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据分类获取商品
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        try {
            List<Product> products = productService.findByCategoryId(categoryId);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 搜索商品
     */
    @GetMapping("/search")
    public Result<List<Product>> searchProducts(@RequestParam String keyword) {
        try {
            List<Product> products = productService.searchByName(keyword);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取商品详情
     */
    @GetMapping("/{id}")
    public Result<Product> getProductDetail(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
