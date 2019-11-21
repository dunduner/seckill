package com.seazen.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


@Component
public class ApiGatewayFilter  extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(ApiGatewayFilter .class);

    @Override
    public boolean shouldFilter() {// 判断是否需要执行该过滤器;
        RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println("Pre1：ApiGatewayFilter-----RequestContext:" + ctx.toString());
        return true;//true需要执行此过滤器，false此过滤器不执行
    }

    @Override
    public Object run() throws ZuulException {
        //获取当前请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //取出当前请求
        HttpServletRequest request = ctx.getRequest();
        logger.info("进入访问过滤器，访问的url:{}，访问的方法：{}",request.getRequestURL(),request.getMethod());
        //从headers中取出key为accessToken值
        String accessToken = request.getHeader("accessToken");//这里我把token写进headers中了
        //这里简单校验下如果headers中没有这个accessToken或者该值为空的情况
        //那么就拦截不入放行，返回401状态码
        if(StringUtils.isEmpty(accessToken)) {
            logger.info("当前请求没有accessToken");
            //使用Zuul来过滤这次请求
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(404);
            // 返回错误内容
            ctx.setResponseBody("{\"result\":\"没有权限\"}");
            ctx.set("isSuccess", false);
            return "当前请求没有accessToken";
        }
        logger.info("请求通过过滤器，accessToken是："+accessToken);
        return null;
    }

   /* @Override
    public Object run() {// 所要执行的具体过滤动作。
        //注意：RequestContext 其实是一个 ConcurrentHashMap
        RequestContext ctx = RequestContext.getCurrentContext();
        //拿到request对象
        HttpServletRequest request = ctx.getRequest();
        //从headers中取出key为accessToken值
        String accessToken = request.getHeader("accessToken");//这里我把token写进headers中了


        System.out.println(String.format("请求方式：%s ,request to 请求路径：%s", request.getMethod(), request.getRequestURL().toString()));
        String username = request.getParameter("username");
        System.out.println("登录的用户名为:"+username);

        if (username != null && "zhangning".equalsIgnoreCase(username)) {
            // 对该请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);//设置相应的状态码
            // 设值，让下一个Filter看到上一个Filter的状态
            ctx.set("isSuccess", true);
            return null;
        } else {
            // 过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
            // 返回错误码
            ctx.setResponseStatusCode(401);
            // 返回错误内容
            ctx.setResponseBody("{\"result\":\"username is not correct!没有权限\"}");
            ctx.set("isSuccess", false);
            return null;
        }
    }*/
//	Type: 定义在请求执行过程中何时被执行;
//	Execution Order: 当存在多个过滤器时，用来指示执行的顺序，值越小就会越早执行;
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

    @Override
    public int filterOrder() {// filterOrder()方法返回的是执行顺序;
//        return PRE_DECORATION_FILTER_ORDER - 1;
        return 0;
    }

}
