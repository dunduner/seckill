package com.seazen.productservice.util.对称加密.PBE;
/**
 * @author zhangning
 * @date 2020/7/24
 */
public class JieMi {


    static String saltPWD ="zhangerning";


    public  void  jiemiMethod(byte[] data, byte[] salt) throws Exception {
        // 解密
        byte[] output = PBE.decrypt(data, saltPWD, salt);
        System.err.println("解密后\t" + new String(output));
    }
}
