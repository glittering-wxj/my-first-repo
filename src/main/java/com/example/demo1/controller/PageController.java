package com.example.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转控制器
 */
@Controller
public class PageController {

    // 用户端页面

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/product/detail")
    public String productDetail() {
        return "product-detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "payment-success";
    }

    @GetMapping("/user/orders")
    public String userOrders() {
        return "user-orders";
    }

    @GetMapping("/user/order/detail")
    public String orderDetail() {
        return "order-detail";
    }

    @GetMapping("/user/address")
    public String userAddress() {
        return "user-address";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "user-profile";
    }

    // 管理员端页面

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/admin-login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/admin-index";
    }

    @GetMapping("/admin/products")
    public String adminProducts() {
        return "admin/admin-products";
    }

    @GetMapping("/admin/orders")
    public String adminOrders() {
        return "admin/admin-orders";
    }

    @GetMapping("/admin/categories")
    public String adminCategories() {
        return "admin/admin-categories";
    }
}
