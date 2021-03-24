package com.seazen.zuul.zuulfilter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.seazen.zuul.entity.GrayRule;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/**
 * 灰度发布
 * 网关对服务的调用
 */
//@Component
public class TagGrayFilter extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(TagGrayFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;//路由过程中的
    }

    @Override
    public boolean shouldFilter() {// 判断是否需要执行该过滤器;
        System.out.println("灰度发布的路由！shouldFilter方法");
        return true;//true需要执行此过滤器，false此过滤器不执行
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String version_value = request.getHeader("version");
        if(StringUtils.isNotEmpty(version_value)){
            //查库找到灰度发布逻辑--根据用户去数据库查 用户的规则
            RibbonFilterContextHolder.getCurrentContext().add("version", version_value);
            logger.info("route {} to {}", request.getRequestURI(), version_value);
        }
        return null;
    }

    //限流要最早
    @Override
    public int filterOrder() {
        return -10;
    }

}
