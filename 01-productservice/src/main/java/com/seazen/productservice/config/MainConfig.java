package com.seazen.productservice.config;

import com.seazen.productservice.entity.Approval;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangning
 * @date 2020/3/30
 */


//@Conditional(MyCondition.class)
@Configuration
public class MainConfig {

    @Bean
    @Conditional({MyCondition.class})
    public String name(){
        return  "路人甲java";
    }

    @Bean
    public Approval chushi(){
        Approval approval = new Approval();
        approval.setLoginName("rizhi");
        approval.setName("zhangsan");
        approval.setNodeName("007");
        return  approval;
    }
}
