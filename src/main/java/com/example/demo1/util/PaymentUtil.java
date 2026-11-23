package com.example.demo1.util;

/**
 * 支付模拟工具类
 */
public class PaymentUtil {

    /**
     * 模拟支付（直接返回成功）
     * @param orderNo 订单号
     * @return 支付结果
     */
    public static boolean mockPayment(String orderNo) {
        // 模拟支付处理时间
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 直接返回支付成功
        return true;
    }
}
