package com.example.demo1.service;

import com.example.demo1.entity.Cart;
import com.example.demo1.entity.CartItem;
import com.example.demo1.entity.Product;
import com.example.demo1.repository.CartItemRepository;
import com.example.demo1.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 购物车服务类
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    /**
     * 获取用户的购物车
     */
    public Cart getOrCreateCart(Long userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        }

        // 创建新购物车
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    /**
     * 获取购物车项列表
     */
    public List<CartItem> getCartItems(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.findByCartId(cart.getId());
    }

    /**
     * 添加商品到购物车
     */
    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        Product product = productService.findById(productId);

        // 检查库存
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }

        // 检查是否已存在该商品
        Optional<CartItem> existItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existItemOpt.isPresent()) {
            // 更新数量
            CartItem item = existItemOpt.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }

        // 创建新的购物车项
        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setProductName(product.getName());
        item.setProductPrice(product.getPrice());
        item.setProductImage(product.getImageUrl());
        item.setChecked(true);

        return cartItemRepository.save(item);
    }

    /**
     * 更新购物车项数量
     */
    public CartItem updateQuantity(Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("购物车项不存在"));

        Product product = productService.findById(item.getProductId());

        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }

        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    /**
     * 更新购物车项选中状态
     */
    public CartItem updateChecked(Long itemId, Boolean checked) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("购物车项不存在"));

        item.setChecked(checked);
        return cartItemRepository.save(item);
    }

    /**
     * 删除购物车项
     */
    public void deleteCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    /**
     * 清空购物车
     */
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    /**
     * 获取选中的购物车项
     */
    public List<CartItem> getCheckedItems(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.findByCartIdAndChecked(cart.getId(), true);
    }
}
