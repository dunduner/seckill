package com.seazen.productservice.util.对称加密;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * DES 对称加密
 * @author zhangning
 * @date 2020/7/24
 *
 * DES有漏洞，所以，产生了3重DES<br>
 * 3重DES的效率比较低，所以产生了AES<br>
 */
public class DESUtil {

    //测试
    public static void main(String[] args) {
        String parameter = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88\n" +
                "}";
        byte[] secretKey = getSecretKey();
        Key key = desKeySpec(secretKey);

        String s1 = encryptForDES(parameter, key);
        System.out.println("================解密中========================");
        String s = decryptForDES(s1, key);
        System.out.println(s);
    }

    /**
     * DES加密
     *
     * @param parameter        加密的明文
     * @param convertSecretKey 秘钥
     * @return
     */
    public static String encryptForDES(String parameter, Key convertSecretKey) {
        byte[] result;
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//ENCRYPT_MODE 加密
            result = cipher.doFinal(parameter.getBytes());
            return Hex.encodeHexString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * DES解密
     *
     * @param parameter        解密的明文
     * @param convertSecretKey 秘钥
     * @return
     */
    public static String decryptForDES(String parameter, Key convertSecretKey) {
        try {
            // 解密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(Hex.decodeHex(parameter));
            String res = new String(result);
//            System.out.println("jdk des 解密结果:" + res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取秘钥byte[]
    public static byte[] getSecretKey() {
        try {
            // 步骤一： 生成key//返回生成指定算法密钥的KeyGenerator对象
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            // 步骤二： 初始化此密钥生成器,使其具有确定的密钥大小
            keyGenerator.init(56);
            // 步骤三：生成一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //步骤四：SecretKey为密钥对象.使用它的getEncoded()方法返回一个密钥(字节数组形式)
            byte[] bs = secretKey.getEncoded();
            return bs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //还原秘钥
    public static Key desKeySpec(byte[] SecretKey_byete) {
        try {
            // key转换【2】转化密钥（还原密钥）
            DESKeySpec desKeySpec = new DESKeySpec(SecretKey_byete); //jdk生成的密钥对象转化成DES规则的密钥对象.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            return convertSecretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
