package com.example.demo1.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * 购物车实体类
 */
@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
    }
}
