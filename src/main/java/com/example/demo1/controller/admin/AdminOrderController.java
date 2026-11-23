package com.example.demo1.controller.admin;

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
 * 订单管理控制器（管理员）
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 检查管理员权限
     */
    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    /**
     * 获取所有订单
     */
    @GetMapping("/list")
    public Result<List<Order>> getAllOrders(HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            List<Order> orders = orderService.findAll();
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    public Result<Order> getOrderDetail(@PathVariable Long orderId, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Order order = orderService.findById(orderId);
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取订单项
     */
    @GetMapping("/{orderId}/items")
    public Result<List<OrderItem>> getOrderItems(@PathVariable Long orderId, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            List<OrderItem> orderItems = orderService.getOrderItems(orderId);
            return Result.success(orderItems);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 发货
     */
    @PostMapping("/ship/{orderId}")
    public Result shipOrder(@PathVariable Long orderId, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            orderService.shipOrder(orderId);
            return Result.success("发货成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
