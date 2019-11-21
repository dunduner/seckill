package com.seazen.dynamicroutezuul.zuulFallbackProvider;


import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ZuulFallbackHandler implements FallbackProvider {
    @Override
    public String getRoute() {
        //getRoute()用于指定应用在哪个服务上
        // 注意: 这里是route的名称，不是服务的名称，
        // 如果这里写成大写PRODUCT-SERVICE将无法起到回退作用
        return "*";//*就是对所有服务进行熔断
    }

    public ClientHttpResponse fallbackResponse(String route) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(("route直达的->《"+route+"》的服务-不可用！").getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null) {
            String reason = cause.getMessage();
        }
        return fallbackResponse(route);
    }
}