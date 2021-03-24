package com.seazen.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/*前置过滤器
 * */
//@Component
public class PreType2ZuulFilter extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(PreType2ZuulFilter.class);

    @Override
    public boolean shouldFilter() {// 判断是否需要执行该过滤器;
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        System.out.println("pre来源：" + request.getRequestURI());
        System.out.println("====token:" + token);
        System.out.println("Pre2：shouldFilter方法-----RequestContext:" + ctx.toString());
        return true;//true需要执行此过滤器，false此过滤器不执行
    }

    @Override
    public Object run() {// 所要执行的具体过滤动作。

        return null;
    }

    //	Type: 定义在请求执行过程中何时被执行;
//	Criteria: 执行的条件，即该过滤器何时会被触发;
//	Action: 具体的动作。
    @Override
    public String filterType() {// filterType()方法是该过滤器的类型;
        return PRE_TYPE;
//     1/PRE_TYPE过滤器: 在请求被路由之前调用, 可用来实现身份验证、在集群中选择请求的微服务、记录调试信息等;
//		2/ROUTING过滤器: 在路由请求时候被调用;
//		3/POST过滤器: 在路由到微服务以后执行, 可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等;
//		4/ERROR过滤器: 在处理请求过程时发生错误时被调用。
//		Zuul过滤器的类型其实也是Zuul过滤器的生命周期，通过下面这张图来了解它们的执行过程。
    }

    //    Execution Order: 当存在多个过滤器时，用来指示执行的顺序，值越小就会越早执行;
    @Override
    public int filterOrder() {// filterOrder()方法返回的是执行顺序;
//        return PRE_DECORATION_FILTER_ORDER;
        //数值越小的越先执行
        return 1;
    }

}
