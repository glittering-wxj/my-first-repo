package com.example.demo1.controller.admin;

import com.example.demo1.entity.Product;
import com.example.demo1.entity.User;
import com.example.demo1.service.ProductService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 商品管理控制器（管理员）
 */
@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {

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
     * 获取所有商品
     */
    @GetMapping("/list")
    public Result<List<Product>> getAllProducts(HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            List<Product> products = productService.findAll();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 创建商品
     */
    @PostMapping("/create")
    public Result<Product> createProduct(@RequestBody Product product, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Product newProduct = productService.create(product);
            return Result.success("创建成功", newProduct);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     */
    @PutMapping("/update")
    public Result<Product> updateProduct(@RequestBody Product product, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            Product updatedProduct = productService.update(product);
            return Result.success("更新成功", updatedProduct);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteProduct(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            productService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上架商品
     */
    @PutMapping("/onSale/{id}")
    public Result onSale(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            productService.onSale(id);
            return Result.success("上架成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 下架商品
     */
    @PutMapping("/offSale/{id}")
    public Result offSale(@PathVariable Long id, HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            productService.offSale(id);
            return Result.success("下架成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新库存
     */
    @PutMapping("/updateStock")
    public Result updateStock(@RequestParam Long id,
                              @RequestParam Integer stock,
                              HttpSession session) {
        try {
            if (!checkAdmin(session)) {
                return Result.error(403, "无权访问");
            }

            productService.updateStock(id, stock);
            return Result.success("库存更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
