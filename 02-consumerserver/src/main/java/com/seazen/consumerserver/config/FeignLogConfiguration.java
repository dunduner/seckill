package com.seazen.consumerserver.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zhangning
 * @date 2020/6/18
 */

@Configuration
public class FeignLogConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        // • NONE: 不记录任何信息。
        // • BASIC: 仅记录请求方法、URL以及响应状态码和执行时间。
        // • HEADERS: 除了记录BASIC级别的信息之外， 还会记录请求和响应的头信息。
        // • FULL: 记录所有请求与响应的明细， 包括头信息、 请求体、 元数据等。
        return Logger.Level.FULL;
    }

    //主要是通过重写 RequestInterceptor的 apply方式实现可以自己控制要穿哪些请求头或者是自己设置新的请求头
//    @Bean   暂时有问题 不能用，打开就熔断
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                System.out.println("===============解决token透传的问题！！！！");
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
//                String token= request.getHeader("token");
//                if (!StringUtils.isEmpty(token)) {
//                    requestTemplate.header("token", token);
//                }
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        template.header(name, values);
                    }
                }
            }
        };
    }


}