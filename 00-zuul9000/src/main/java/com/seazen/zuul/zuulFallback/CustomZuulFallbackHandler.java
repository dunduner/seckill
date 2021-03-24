package com.seazen.zuul.zuulFallback;


import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 过滤器的容错
 */
@Component
public class CustomZuulFallbackHandler implements FallbackProvider {
    //表明为那个微服务回退
    //服务id ，
    @Override
    public String getRoute() {
        //getRoute()用于指定应用在哪个服务上
        // 注意: 这里是route的名称，不是服务的名称，
        // 如果这里写成大写PRODUCT-SERVICE将无法起到回退作用
        return "*";//*就是对所有服务进行熔断
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return fallbackResponse(route, cause.getMessage());
    }

    public ClientHttpResponse fallbackResponse(String route, String reason) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.BAD_REQUEST.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                //消费者不可达-可发送邮件
                return new ByteArrayInputStream(("route直达的->《" + route + "》的服务-不可用！|||异常为：" + reason).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }


}