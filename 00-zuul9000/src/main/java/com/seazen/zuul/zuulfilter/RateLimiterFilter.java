package com.seazen.zuul.zuulfilter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/**
 * 网关限流
 */
//@Component
public class RateLimiterFilter extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

    private static int count=1;
    /**
     * 参数1 就是1秒一个
     * 参数0.1   10秒1个
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1);


    @Override
    public boolean shouldFilter() {// 判断是否需要执行该过滤器;
        return true;//true需要执行此过滤器，false此过滤器不执行
    }

    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()){
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletRequest request = currentContext.getRequest();
            System.out.println("拿不到令牌了，被限流了"+count++);
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());

            //如果后面拿到limit是false，就shouldFilter都返回fasle不执行--需要在其他过滤器中return这个布尔值
            currentContext.set("limit",false);
        }
        return null;
    }

    @Override
    public String filterType() {// filterType()方法是该过滤器的类型;
        return PRE_TYPE;
    }

    //限流要最早
    @Override
    public int filterOrder() {
        return -10;
    }

}
