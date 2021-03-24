package com.seazen.productservice.config;

import com.seazen.productservice.service.Iservice;
import com.seazen.productservice.service.impl.Iservice1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangning
 * @date 2020/3/30
 */


@Configuration
public class Iservce_1_Config {

    @Conditional(OnmissBeanCondition.class)
    @Bean
    public Iservice register1 (){
        return new Iservice1();
    }


}
