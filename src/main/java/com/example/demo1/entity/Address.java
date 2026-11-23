package com.example.demo1.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * 收货地址实体类
 */
@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String receiver;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 50)
    private String province;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String district;

    @Column(nullable = false, length = 200)
    private String detail;

    // 是否为默认地址
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
    }
}
