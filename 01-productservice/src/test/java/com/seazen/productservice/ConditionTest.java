package com.seazen.productservice;

import com.seazen.productservice.config.IserviceMainConfig;
import com.seazen.productservice.config.MainConfig;
import com.seazen.productservice.config.test_dev_pro.MyHuanjingConfig;
import com.seazen.productservice.entity.Approval;
import com.seazen.productservice.service.Iservice;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author zhangning
 * @date 2020/3/30
 */
public class ConditionTest {

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        Map<String, String> serviceMap = context.getBeansOfType(String.class);
        serviceMap.forEach((beanName, bean) -> {
            System.out.println(String.format("%s->%s", beanName, bean));
        });
        Map<String, Approval> beansOfType = context.getBeansOfType(Approval.class);
        beansOfType.forEach((chushi,Approval)->{
            System.out.println(String.format("%S-%s",chushi,Approval));
        });
    }

    @Test
    public void iserviceConditionTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IserviceMainConfig.class);
        Map<String, Iservice> serviceMap = context.getBeansOfType(Iservice.class);
        System.out.println(serviceMap.size());
        serviceMap.forEach((beanName, bean) -> {
            System.out.println(String.format("%s->%s", beanName, bean));
        });
    }
    @Test
    public void EnvConditionHuanJingTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyHuanjingConfig.class);
        System.out.println(context.getBean("name").toString());

    }
}
