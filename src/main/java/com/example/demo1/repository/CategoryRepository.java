package com.example.demo1.repository;

import com.example.demo1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类数据访问层
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据父分类ID查找子分类
     */
    List<Category> findByParentId(Long parentId);

    /**
     * 根据父分类ID和排序号查找分类
     */
    List<Category> findByParentIdOrderBySortOrderAsc(Long parentId);
}
