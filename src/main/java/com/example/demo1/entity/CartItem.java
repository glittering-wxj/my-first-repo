package com.example.demo1.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车项实体类
 */
@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity = 1;

    // 商品快照-名称
    @Column(name = "product_name", length = 100)
    private String productName;

    // 商品快照-价格
    @Column(name = "product_price", precision = 10, scale = 2)
    private BigDecimal productPrice;

    // 商品快照-图片
    @Column(name = "product_image", length = 200)
    private String productImage;

    // 是否选中（用于结算）
    @Column(nullable = false)
    private Boolean checked = true;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
    }
}
