package com.example.demo1.util;

import java.security.MessageDigest;

/**
 * MD5 加密工具类
 */
public class MD5Util {

    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            StringBuilder hexValue = new StringBuilder();
            for (byte b : byteDigest) {
                int val = ((int) b) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
