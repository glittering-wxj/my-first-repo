package com.example.demo1.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String nickname;

    // 角色：user-普通用户, admin-管理员
    @Column(nullable = false, length = 20)
    private String role = "user";

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
    }
}
