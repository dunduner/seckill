package com.seazen.productservice.config;

import com.seazen.productservice.service.Iservice;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author zhangning
 * @date 2020/3/30
 */
public class OnmissBeanCondition  implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取bean工厂
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();

        Map<String, Iservice> beansOfType = beanFactory.getBeansOfType(Iservice.class);
        boolean empty = beansOfType.isEmpty();
        System.out.println(empty);
        //如果为空 就不拦截
        return empty;
    }
}
