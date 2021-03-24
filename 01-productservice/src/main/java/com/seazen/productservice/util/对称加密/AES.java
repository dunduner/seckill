package com.seazen.productservice.util.对称加密;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * AES是DES的高级替代，也是目前使用最多的对称加密算法
 * @author zhangning
 * @date 2020/7/24
 *
 */
public class AES {

    static  String string = "{\n" +
            "    \"name\": \"BeJson\",\n" +
            "    \"url\": \"http://www.张宁.com\",\n" +
            "    \"page\": 88\n" +
            "}";


    public static void main(String[] args) {
        AES.jdkAES();
    }

    public static void jdkAES(){
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //设置密钥长度
            keyGenerator.init(128);
            //生成密钥对象
            SecretKey secretKey = keyGenerator.generateKey();
            //获取密钥
            byte[] keyBytes = secretKey.getEncoded();
            //key转换
            Key key = new SecretKeySpec(keyBytes,"AES");

            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            //初始化，设置为加密
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(string.getBytes());
            System.out.println("jdk aes encrypt: " + Base64.encodeBase64String(result));


            //初始化,设置为解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes desrypt:" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
