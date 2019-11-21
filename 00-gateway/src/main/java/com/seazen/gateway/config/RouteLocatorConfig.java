package com.seazen.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouteLocatorConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        System.out.println("路由初始化！！！！！！！！！！！！！！！！！！！！！！");
        return builder.routes()
                .route(r -> r.path("/baidu")
                        .uri("http://baidu.com:80/")
                )
                .route("websocket_route", r -> r.path("/apitopic1/**")
                        .uri("ws://127.0.0.1:6605"))

                //http://localhost:6604/userapi3/product/employee/list
                .route(r -> r.path("/userapi3/**")
                        //TODO
                        //addResponseHeader不太明白如何使用
                        .filters(f -> f.addResponseHeader("X-AnotherHeader", "testapi3")
                                .stripPrefix(1))
                        .uri("lb://product-server/")
                )
                .build();
    }
}
