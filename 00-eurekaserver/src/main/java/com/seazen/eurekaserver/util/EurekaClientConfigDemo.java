package com.seazen.eurekaserver.util;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

//获取eureka服务等信息
public class EurekaClientConfigDemo {
    public static void main(String[] args) {

        EurekaServerAll eurekaServerAll = new EurekaServerAll();
        List<String> allServiceAddr = eurekaServerAll.getAllServiceAddr("localhost", "8761", "PRODUCT-SERVER");
        for (String addr : allServiceAddr) {
            System.out.println(addr);
        }
    }
}
