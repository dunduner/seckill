package com.seazen.productservice.util.对称加密.PBE;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.SecretKey;

/**
 * PBE算法结合了消息摘要算法和对称加密算法的优点。PBE是基于口令的加密
 *
 * @author zhangning
 * @date 2020/7/24
 * <p>
 * 对称加密算法之PBE的特点概述，本质上是对DES/3DES/AES对称加密算法的包装，不是新的算法，不过也是最为牛逼的一种方式。
 * 盐：指加密的随机字符串或者口令等，也可以人为是一些扰码，防止密码的暴力破解
 *
 * 加密算法安全等级：PBE>AES>3DES>DES
 */
public class PBE {
    public static final String ALGORUTHM ="PBEWITHMD5andDES";
    /**
     * 迭代次数
     */
    public static final int ITERATION_COUN = 100;

    /**
     * "盐"初始化<br>
     * 盐长度必须为8字节
     *
     * @return byte[] 盐
     * @throws Exception
     */
    public static byte[] initSalt() throws Exception {
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        //产出“盐”
        byte[] salt = random.generateSeed(8);
        return salt;
    }

    /**
     * 转换密钥
     *
     * @param password 密码
     * @return Key密钥
     * @throws Exception
     */

    private static Key toKey(String password) throws Exception {
        //密钥材料转换
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORUTHM);
        //生成密钥
        SecretKey secretkey = keyFactory.generateSecret(keySpec);
        return secretkey;
    }

    /**
     * 加密
     *
     * @param data     数据
     * @param password 密钥
     * @param salt     盐
     * @return byte[] 加密数据
     * @throws Exception
     */

    public static byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception {
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参考数据
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUN);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORUTHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param data     数据
     * @param password 密码
     * @param salt     盐
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception {
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUN);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORUTHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        //执行操作
        return cipher.doFinal(data);
    }
}