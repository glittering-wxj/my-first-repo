package com.example.demo1.controller.admin;

import com.example.demo1.entity.Category;
import com.example.demo1.entity.Product;
import com.example.demo1.entity.User;
import com.example.demo1.service.CategoryService;
import com.example.demo1.service.ProductService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 分类管理控制器（管理员）
 */
@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * 检查管理员权限
     */
    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    /**
     * 获取所有分类
     */
    @GetMapping("/list")
    public Result<List<Category>> getAllCategories(HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            List<Category> categories = categoryService.findAll();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public Result<Category> getCategoryById(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Category category = categoryService.findById(id);
            return Result.success(category);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 创建分类
     */
    @PostMapping("/create")
    public Result<Category> createCategory(@RequestBody Category category, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Category newCategory = categoryService.create(category);
            return Result.success("创建成功", newCategory);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新分类
     */
    @PutMapping("/update")
    public Result<Category> updateCategory(@RequestBody Category category, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Category updatedCategory = categoryService.update(category);
            return Result.success("更新成功", updatedCategory);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteCategory(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            categoryService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取分类下的所有商品
     */
    @GetMapping("/{id}/products")
    public Result<List<Product>> getProductsByCategoryId(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            List<Product> products = productService.findAllByCategoryId(id);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
