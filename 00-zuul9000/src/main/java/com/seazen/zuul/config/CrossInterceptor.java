package com.seazen.zuul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class CrossInterceptor implements Filter {


    private final Logger logger = LoggerFactory.getLogger(CrossInterceptor.class);

    //springboot/springcloud zuul 跨域设置
    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response =  (HttpServletResponse) res;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,clientType,Access-Token,doctorId,token");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "1800");// 30 min
        chain.doFilter(request, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }
}