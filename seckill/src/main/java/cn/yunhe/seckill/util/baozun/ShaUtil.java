package cn.yunhe.seckill.util.baozun;

import java.security.MessageDigest;

/**
 * sha-1加密
 * 与在线工具加密方式一样
 * 签名(由登录名+密码+租户+租户秘钥+saasTenantCode  通过SHA512 生成)，客户端与服务端验签使用
 * @author Justin Hu
 */
public class ShaUtil {


    public static String encode512(String decript) {

        return encode(decript, "SHA-512");
    }

    public static String encode256(String decript) {

        return encode(decript, "SHA-256");
    }

    public static String encode(String decript) {

        return encode(decript, "SHA-1");
    }

    public static String encode(String decript, String type) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(type);
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
// Create Hex String
            StringBuffer hexString = new StringBuffer();
// 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {

        }
        return null;

    }
//签名(由登录名+密码+租户+租户秘钥+saasTenantCode  通过SHA512 生成)，客户端与服务端验签使用
    public static void main(String[] args) {
        String sign = "jm014974Zhn-8930961uac_test12345678baozun";
        String newSign = ShaUtil.encode512(sign);
        System.out.println(newSign);
    }
}