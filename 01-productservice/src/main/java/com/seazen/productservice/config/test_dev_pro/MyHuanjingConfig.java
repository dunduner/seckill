package com.seazen.productservice.config.test_dev_pro;

import com.seazen.productservice.config.Iservce_1_Config;
import com.seazen.productservice.config.Iservce_2_Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangning
 * @date 2020/3/30
 */

@Configuration
@Import({DevBeanConfig.class, ProdBeanConfig.class,TestBeanConfig.class})
public class MyHuanjingConfig {
}
