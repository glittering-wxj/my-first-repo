package com.example.demo1.service;

import com.example.demo1.entity.Address;
import com.example.demo1.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货地址服务类
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     * 获取用户的所有地址
     */
    public List<Address> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    /**
     * 获取用户的默认地址
     */
    public Address getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndIsDefault(userId, true)
                .orElse(null);
    }

    /**
     * 根据ID查找地址
     */
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("地址不存在"));
    }

    /**
     * 创建地址
     */
    @Transactional
    public Address create(Address address) {
        // 如果设置为默认地址，先取消其他默认地址
        if (address.getIsDefault()) {
            Address defaultAddress = getDefaultAddress(address.getUserId());
            if (defaultAddress != null) {
                defaultAddress.setIsDefault(false);
                addressRepository.save(defaultAddress);
            }
        }

        return addressRepository.save(address);
    }

    /**
     * 更新地址
     */
    @Transactional
    public Address update(Address address) {
        Address existAddress = findById(address.getId());

        existAddress.setReceiver(address.getReceiver());
        existAddress.setPhone(address.getPhone());
        existAddress.setProvince(address.getProvince());
        existAddress.setCity(address.getCity());
        existAddress.setDistrict(address.getDistrict());
        existAddress.setDetail(address.getDetail());

        // 如果设置为默认地址，先取消其他默认地址
        if (address.getIsDefault() && !existAddress.getIsDefault()) {
            Address defaultAddress = getDefaultAddress(address.getUserId());
            if (defaultAddress != null) {
                defaultAddress.setIsDefault(false);
                addressRepository.save(defaultAddress);
            }
        }

        existAddress.setIsDefault(address.getIsDefault());

        return addressRepository.save(existAddress);
    }

    /**
     * 删除地址
     */
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    /**
     * 设置默认地址
     */
    @Transactional
    public void setDefault(Long id, Long userId) {
        // 取消当前默认地址
        Address defaultAddress = getDefaultAddress(userId);
        if (defaultAddress != null) {
            defaultAddress.setIsDefault(false);
            addressRepository.save(defaultAddress);
        }

        // 设置新的默认地址
        Address address = findById(id);
        address.setIsDefault(true);
        addressRepository.save(address);
    }
}
