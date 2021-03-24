package com.seazen.consumerserver.gray;

import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author zhangning
 * @date 2020/7/16
 * <p>
 * 定义切面
 */

@Aspect
@Component
public class AspectGrayRule {

    @Pointcut("execution(* com.seazen.consumerserver.controller.*Controller*.*(..))")
    private void anyMethod() {
    }

    @Before(value = "anyMethod()")
    public void before(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes1 = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = requestAttributes1.getRequest();
        String version_value = request.getHeader("version");
        System.out.println("进入到切面，version是：" + version_value);
        //方案一：放到Threadlocal进行后续取值
//        HashMap<String, String> amap = new HashMap<>();
//        amap.put("version", version);
//        RibbonParameters.set(amap);
        //方案二：使用插件
        if(StringUtils.isNotEmpty(version_value)){
            //查库找到灰度发布逻辑--根据用户去数据库查 用户的规则
            RibbonFilterContextHolder.getCurrentContext().add("version", version_value);
        }

    }
}
