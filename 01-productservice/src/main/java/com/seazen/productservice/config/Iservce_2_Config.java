package com.seazen.productservice.config;

import com.seazen.productservice.service.Iservice;
import com.seazen.productservice.service.impl.Iservice1;
import com.seazen.productservice.service.impl.Iservice2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangning
 * @date 2020/3/30
 */


@Configuration
public class Iservce_2_Config {

    @Conditional(OnmissBeanCondition.class)
    @Bean
    public Iservice register2 (){
        return new Iservice2();
    }


}
