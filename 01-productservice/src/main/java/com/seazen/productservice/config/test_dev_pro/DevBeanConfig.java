package com.seazen.productservice.config.test_dev_pro;

import com.seazen.productservice.annotation.EnvConditional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangning
 * @date 2020/3/30
 */
@Configuration
@EnvConditional(EnvConditional.Env.DEV)
public class DevBeanConfig {

    @Bean
    public String name(){
        return "我是开发环境";
    }

}
