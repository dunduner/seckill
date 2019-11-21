package cn.yunhe.seckill.config;


import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @program: ryx_springcloud
 *
 * @description:
 *
 * @author: renyuanxin
 *
 * @create: 2019-02-02 09:20
 **/
@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class})
@EnableFeignClients(basePackages = "cn.yunhe.seckill")
@Configuration
public class FeignConfig {

    @Bean
    public HttpMessageConverters customConverters(){
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        messageConverters.add(gsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }
}