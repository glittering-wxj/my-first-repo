package com.example.demo1.repository;

import com.example.demo1.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 收货地址数据访问层
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * 根据用户ID查找所有地址
     */
    List<Address> findByUserId(Long userId);

    /**
     * 根据用户ID查找默认地址
     */
    Optional<Address> findByUserIdAndIsDefault(Long userId, Boolean isDefault);
}
