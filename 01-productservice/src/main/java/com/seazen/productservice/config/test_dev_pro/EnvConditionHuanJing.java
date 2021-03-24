package com.seazen.productservice.config.test_dev_pro;

import com.seazen.productservice.annotation.EnvConditional;
import com.seazen.productservice.service.Iservice;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * @author zhangning
 * @date 2020/3/30
 */
public class EnvConditionHuanJing implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //当前需要使用的环境
        EnvConditional.Env curEnv = EnvConditional.Env.DEV; //@1
        //拿到自定义注解的 类的全名
        String name = EnvConditional.class.getName();

        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(name);

        //获取使用条件的类上的EnvCondition注解中对应的环境
        EnvConditional.Env env = (EnvConditional.Env) metadata.getAllAnnotationAttributes(EnvConditional.class.getName()).get("value").get(0);
        return env.equals(curEnv);
    }
}
