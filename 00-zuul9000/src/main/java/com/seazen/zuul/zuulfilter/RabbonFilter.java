package com.seazen.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


import java.net.URI;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author zhangning
 * @date 2020/7/21
 */
//@Component
public class RabbonFilter extends ZuulFilter {

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        //localhost:9000/v1/xiaofei/employee错误url/2
        if (requestURI.contains("/xiaofei")) {
            //(路由到具体服务)--知道服务，地址不统一的用下面2行
            currentContext.set(FilterConstants.SERVICE_ID_KEY, "CONSUMER-SERVICE");
            currentContext.set(FilterConstants.REQUEST_URI_KEY, "/employee/2");
            //（路由到具体地址）不知道服务名，只知道地址的用下面的指定地址
            //currentContext.setRouteHost(new URI("http://locahost:8081/employee/2").toURL());

        }
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("新老路径的路由！过滤器，给过了！");
        return true;
    }


}
