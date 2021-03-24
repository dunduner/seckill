package com.seazen.productservice.config.test_dev_pro;


import com.seazen.productservice.annotation.EnvConditional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnvConditional(EnvConditional.Env.PROD) //@1
public class ProdBeanConfig {
    @Bean
    public String name() {
        return "我是生产环境!";
    }
}