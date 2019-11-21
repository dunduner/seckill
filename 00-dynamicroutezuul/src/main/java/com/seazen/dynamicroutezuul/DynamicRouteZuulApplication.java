package com.seazen.dynamicroutezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@EnableZuulProxy // 开启zuul代理模式
@EnableDiscoveryClient  //开启服务注册与发现功能
public class DynamicRouteZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicRouteZuulApplication.class, args);
    }

    /**
     * git地址：      https://github.com/dunduner/springcloudconfig/blob/master/dynamiczuul-dev.yml
     * 刷新地址：POST http://127.0.0.1:9001/actuator/refresh
     * 路由查看地址：GET http://127.0.0.1:9001/actuator/routes
     * @return
     */
    @Bean
    @Primary
    //该注解来使 zuul 的配置内容动态化
    @RefreshScope
    @ConfigurationProperties(prefix = "zuul")
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}
