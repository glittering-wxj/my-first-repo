package com.example.demo1.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号生成工具类
 */
public class OrderNoUtil {

    /**
     * 生成订单号：时间戳 + 随机数
     */
    public static String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());

        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000; // 生成1000-9999的随机数

        return timeStr + randomNum;
    }
}
