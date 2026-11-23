package com.example.demo1.service;

import com.example.demo1.entity.*;
import com.example.demo1.repository.OrderItemRepository;
import com.example.demo1.repository.OrderRepository;
import com.example.demo1.util.OrderNoUtil;
import com.example.demo1.util.PaymentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单服务类
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(Long userId, Long addressId) {
        // 获取选中的购物车项
        List<CartItem> cartItems = cartService.getCheckedItems(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("请选择要购买的商品");
        }

        // 获取收货地址
        Address address = addressService.findById(addressId);

        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            BigDecimal itemTotal = item.getProductPrice().multiply(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // 创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(0); // 待支付
        order.setReceiver(address.getReceiver());
        order.setPhone(address.getPhone());
        order.setAddress(address.getProvince() + " " + address.getCity() + " " +
                address.getDistrict() + " " + address.getDetail());

        order = orderRepository.save(order);

        // 创建订单项
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductImage(cartItem.getProductImage());
            orderItem.setProductPrice(cartItem.getProductPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getProductPrice().multiply(new BigDecimal(cartItem.getQuantity())));

            orderItemRepository.save(orderItem);

            // 删除购物车项
            cartService.deleteCartItem(cartItem.getId());
        }

        return order;
    }

    /**
     * 支付订单
     */
    @Transactional
    public boolean payOrder(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态异常");
        }

        // 模拟支付
        boolean payResult = PaymentUtil.mockPayment(orderNo);

        if (payResult) {
            // 更新订单状态
            order.setStatus(1); // 已支付
            order.setPayTime(new Date());
            orderRepository.save(order);

            // 减少库存
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
            for (OrderItem item : orderItems) {
                productService.decreaseStock(item.getProductId(), item.getQuantity());
            }

            return true;
        }

        return false;
    }

    /**
     * 获取用户的所有订单
     */
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    /**
     * 根据订单号查找订单
     */
    public Order findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    /**
     * 根据ID查找订单
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    /**
     * 获取订单项列表
     */
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    /**
     * 取消订单
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findById(orderId);

        if (order.getStatus() != 0) {
            throw new RuntimeException("只能取消待支付的订单");
        }

        order.setStatus(4); // 已取消
        orderRepository.save(order);
    }

    /**
     * 确认收货
     */
    @Transactional
    public void confirmOrder(Long orderId) {
        Order order = findById(orderId);

        if (order.getStatus() != 2) {
            throw new RuntimeException("订单状态异常");
        }

        order.setStatus(3); // 已完成
        orderRepository.save(order);
    }

    /**
     * 获取所有订单（管理员）
     */
    public List<Order> findAll() {
        return orderRepository.findAllByOrderByCreateTimeDesc();
    }

    /**
     * 发货（管理员）
     */
    @Transactional
    public void shipOrder(Long orderId) {
        Order order = findById(orderId);

        if (order.getStatus() != 1) {
            throw new RuntimeException("只能发货已支付的订单");
        }

        order.setStatus(2); // 已发货
        orderRepository.save(order);
    }
}
