package com.example.demo1.controller;

import com.example.demo1.entity.CartItem;
import com.example.demo1.entity.User;
import com.example.demo1.service.CartService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车列表
     */
    @GetMapping("/list")
    public Result<List<CartItem>> getCartItems(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            List<CartItem> cartItems = cartService.getCartItems(user.getId());
            return Result.success(cartItems);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    public Result<CartItem> addToCart(@RequestParam Long productId,
                                       @RequestParam(defaultValue = "1") Integer quantity,
                                       HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            CartItem cartItem = cartService.addToCart(user.getId(), productId, quantity);
            return Result.success("添加成功", cartItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新购物车项数量
     */
    @PutMapping("/updateQuantity")
    public Result<CartItem> updateQuantity(@RequestParam Long itemId,
                                            @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.updateQuantity(itemId, quantity);
            return Result.success("更新成功", cartItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新购物车项选中状态
     */
    @PutMapping("/updateChecked")
    public Result<CartItem> updateChecked(@RequestParam Long itemId,
                                           @RequestParam Boolean checked) {
        try {
            CartItem cartItem = cartService.updateChecked(itemId, checked);
            return Result.success(cartItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除购物车项
     */
    @DeleteMapping("/delete/{itemId}")
    public Result deleteCartItem(@PathVariable Long itemId) {
        try {
            cartService.deleteCartItem(itemId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    public Result clearCart(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            cartService.clearCart(user.getId());
            return Result.success("清空成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取选中的购物车项
     */
    @GetMapping("/checked")
    public Result<List<CartItem>> getCheckedItems(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            List<CartItem> cartItems = cartService.getCheckedItems(user.getId());
            return Result.success(cartItems);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
