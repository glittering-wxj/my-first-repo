package com.example.demo1.controller;

import com.example.demo1.entity.Address;
import com.example.demo1.entity.User;
import com.example.demo1.service.AddressService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 收货地址控制器
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取用户的所有地址
     */
    @GetMapping("/list")
    public Result<List<Address>> getAddressList(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            List<Address> addresses = addressService.findByUserId(user.getId());
            return Result.success(addresses);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取默认地址
     */
    @GetMapping("/default")
    public Result<Address> getDefaultAddress(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            Address address = addressService.getDefaultAddress(user.getId());
            return Result.success(address);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID获取地址
     */
    @GetMapping("/{id}")
    public Result<Address> getAddress(@PathVariable Long id) {
        try {
            Address address = addressService.findById(id);
            return Result.success(address);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 创建地址
     */
    @PostMapping("/create")
    public Result<Address> createAddress(@RequestBody Address address, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            address.setUserId(user.getId());
            Address newAddress = addressService.create(address);
            return Result.success("添加成功", newAddress);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新地址
     */
    @PutMapping("/update")
    public Result<Address> updateAddress(@RequestBody Address address, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            address.setUserId(user.getId());
            Address updatedAddress = addressService.update(address);
            return Result.success("更新成功", updatedAddress);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除地址
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteAddress(@PathVariable Long id) {
        try {
            addressService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/setDefault/{id}")
    public Result setDefaultAddress(@PathVariable Long id, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(401, "请先登录");
            }

            addressService.setDefault(id, user.getId());
            return Result.success("设置成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
