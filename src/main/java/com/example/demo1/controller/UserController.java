package com.example.demo1.controller;

import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import com.example.demo1.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        try {
            User newUser = userService.register(user);
            // 不返回密码
            newUser.setPassword(null);
            return Result.success("注册成功", newUser);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@RequestParam String usernameOrPhone,
                              @RequestParam String password,
                              HttpSession session) {
        try {
            User user = userService.login(usernameOrPhone, password);
            // 将用户信息存入 session
            session.setAttribute("user", user);
            // 不返回密码
            user.setPassword(null);
            return Result.success("登录成功", user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        session.removeAttribute("user");
        return Result.success("退出成功");
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error(401, "未登录");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                return Result.error(401, "未登录");
            }

            user.setId(currentUser.getId());
            User updatedUser = userService.updateUser(user);

            // 更新 session
            session.setAttribute("user", updatedUser);

            updatedUser.setPassword(null);
            return Result.success("更新成功", updatedUser);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    public Result changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                return Result.error(401, "未登录");
            }

            userService.changePassword(currentUser.getId(), oldPassword, newPassword);
            return Result.success("密码修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
