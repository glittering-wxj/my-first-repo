package com.example.demo1.service;

import com.example.demo1.entity.User;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务类
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户注册
     */
    public User register(User user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查手机号是否已存在
        if (user.getPhone() != null && userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new RuntimeException("手机号已被注册");
        }

        // 密码加密
        user.setPassword(MD5Util.encrypt(user.getPassword()));

        // 设置默认角色
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }

        return userRepository.save(user);
    }

    /**
     * 用户登录
     */
    public User login(String usernameOrPhone, String password) {
        // 查找用户
        Optional<User> userOpt = userRepository.findByUsernameOrPhone(usernameOrPhone, usernameOrPhone);

        if (!userOpt.isPresent()) {
            throw new RuntimeException("用户名或密码错误");
        }

        User user = userOpt.get();

        // 验证密码
        if (!user.getPassword().equals(MD5Util.encrypt(password))) {
            throw new RuntimeException("用户名或密码错误");
        }

        return user;
    }

    /**
     * 根据ID查找用户
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    /**
     * 更新用户信息
     */
    public User updateUser(User user) {
        User existUser = findById(user.getId());

        if (user.getNickname() != null) {
            existUser.setNickname(user.getNickname());
        }
        if (user.getEmail() != null) {
            existUser.setEmail(user.getEmail());
        }
        if (user.getPhone() != null) {
            existUser.setPhone(user.getPhone());
        }

        return userRepository.save(existUser);
    }

    /**
     * 修改密码
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);

        if (!user.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            throw new RuntimeException("原密码错误");
        }

        user.setPassword(MD5Util.encrypt(newPassword));
        userRepository.save(user);
    }
}
