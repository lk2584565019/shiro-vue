package com.lk.uitl;

import com.lk.common.Constant;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Random;

/**
 * @author LvKang
 * @createTime 2021-10-21
 * 密码加密算法采用 md5算法 + 随机盐 + hash散列  这样就保证了密码的安全性
 */

public class SaltUtils {

    //随机盐的生成
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    /**
     * 密码校验
     * @param validPassword  登陆时的密码
     * @param salt   用户随机盐
     * @param userPassword   用户存储的加密密码
     * @return
     */
    public static Boolean validPassword(String validPassword,String salt,String userPassword){
        Md5Hash md5Hash = new Md5Hash(validPassword, salt, Constant.HASH_1024);
        return md5Hash.toHex().equals(userPassword);
    }

    /*
     * 密码生成
     */
    public static String getPassword(String password ,String salt){
        return new Md5Hash(password, salt, Constant.HASH_1024).toHex();
    }
}
