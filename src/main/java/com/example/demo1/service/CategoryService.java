package com.example.demo1.service;

import com.example.demo1.entity.Category;
import com.example.demo1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类服务类
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 获取所有分类
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * 根据父分类ID获取子分类
     */
    public List<Category> findByParentId(Long parentId) {
        return categoryRepository.findByParentIdOrderBySortOrderAsc(parentId);
    }

    /**
     * 获取顶级分类
     */
    public List<Category> findTopCategories() {
        return findByParentId(0L);
    }

    /**
     * 根据ID查找分类
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
    }

    /**
     * 创建分类
     */
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * 更新分类
     */
    public Category update(Category category) {
        Category existCategory = findById(category.getId());
        existCategory.setName(category.getName());
        existCategory.setDescription(category.getDescription());
        existCategory.setParentId(category.getParentId());
        existCategory.setSortOrder(category.getSortOrder());
        return categoryRepository.save(existCategory);
    }

    /**
     * 删除分类
     */
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
