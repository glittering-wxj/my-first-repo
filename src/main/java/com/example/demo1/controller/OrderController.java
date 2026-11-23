package com.example.demo1.controller;

import com.example.demo1.entity.Order;
import com.example.demo1.entity.OrderItem;
import com.example.demo1.entity.User;
import com.example.demo1.service.OrderService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单控制器（用户端）
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestParam Long addressId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            Order order = orderService.createOrder(user.getId(), addressId);
            return Result.success("订单创建成功", order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay")
    public Result payOrder(@RequestParam String orderNo) {
        try {
            boolean success = orderService.payOrder(orderNo);
            if (success) {
                return Result.success("支付成功");
            } else {
                return Result.error("支付失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户的所有订单
     */
    @GetMapping("/list")
    public Result<List<Order>> getOrderList(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            List<Order> orders = orderService.findByUserId(user.getId());
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据订单号获取订单详情
     */
    @GetMapping("/detail/{orderNo}")
    public Result<Order> getOrderDetail(@PathVariable String orderNo) {
        try {
            Order order = orderService.findByOrderNo(orderNo);
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取订单项
     */
    @GetMapping("/{orderId}/items")
    public Result<List<OrderItem>> getOrderItems(@PathVariable Long orderId) {
        try {
            List<OrderItem> orderItems = orderService.getOrderItems(orderId);
            return Result.success(orderItems);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel/{orderId}")
    public Result cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return Result.success("订单已取消");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 确认收货
     */
    @PostMapping("/confirm/{orderId}")
    public Result confirmOrder(@PathVariable Long orderId) {
        try {
            orderService.confirmOrder(orderId);
            return Result.success("确认收货成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
