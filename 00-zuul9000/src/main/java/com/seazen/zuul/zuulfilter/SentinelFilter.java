package com.seazen.zuul.zuulfilter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

/**
 * @author zhangning
 * @date 2020/7/27
 * <p>
 * 阿里的sentinel 限流
 */
//@Component
public class SentinelFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        Entry entry = null;

        try {
            entry = SphU.entry("helloworld");


            //业务逻辑start
            System.out.println("正常请求业务！");
            //业务逻辑end

        } catch (BlockException e) {
            System.out.println("阻塞了！");
//            e.printStackTrace();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


}
