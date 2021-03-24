package com.seazen.productservice.util.对称加密.PBE;
import org.apache.commons.codec.binary.Base64;

/**
 * @author zhangning
 * @date 2020/7/24
 */
public class PBETest {

    static String parameter = "{\n" +
            "    \"name\": \"BeJson\",\n" +
            "    \"url\": \"http://www.bejson.com\",\n" +
            "    \"page\": 88\n" +
            "}";

    static String saltPWD ="zhangerning";

    public static void main(String[] args) throws Exception {
        System.err.println("原文：\t" + parameter);
        byte[] input = parameter.getBytes();

//        System.err.println("密码：\t" + saltPWD);

        // 初始化盐
        byte[] salt = PBE.initSalt();
//        System.err.println("盐：\t" + Base64.encodeBase64String(salt));

        // 加密
        byte[] data = PBE.encrypt(input, saltPWD, salt);
//        System.out.println(data);
        String base64String = Base64.encodeBase64String(data);
//        System.err.println("加密后\t" + base64String);


        JieMi jieMi = new JieMi();
        System.out.println("==============传递数据 给其他系统");
        //盐是随机的 每次要传过去
        jieMi.jiemiMethod(data,salt);
    }

}
