package com.example.demo1.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 商品分类实体类
 */
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    // 父分类ID，0表示顶级分类
    @Column(name = "parent_id")
    private Long parentId = 0L;

    // 排序号
    @Column(name = "sort_order")
    private Integer sortOrder = 0;
}
