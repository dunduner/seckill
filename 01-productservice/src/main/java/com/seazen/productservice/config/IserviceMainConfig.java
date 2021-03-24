package com.seazen.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangning
 * @date 2020/3/30
 */

@Configuration
@Import({Iservce_1_Config.class,Iservce_2_Config.class})
public class IserviceMainConfig {

}
