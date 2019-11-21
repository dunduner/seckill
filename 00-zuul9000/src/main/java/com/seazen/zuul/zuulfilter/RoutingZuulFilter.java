package com.seazen.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

//@Component
public class RoutingZuulFilter extends ZuulFilter {
    @Override
    public boolean shouldFilter() {
        System.out.println("Routing1！RoutingZuulFilter执行了！");
        return true;
    }
    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }
    @Override
    public int filterOrder() {
        return 0;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Boolean isSuccess = (Boolean)ctx.get("isSuccess");
        return null;
    }
}
