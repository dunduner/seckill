package com.seazen.zuul.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.seazen.zuul.exception.ExceptionUtil;
import org.springframework.stereotype.Service;

/**
 * @author zhangning
 * @date 2020/7/27
 */

@Service
public class SentinelService {

    @SentinelResource(value = "SentinelService.success", blockHandlerClass = {ExceptionUtil.class}, blockHandler = "handleException")
    public String success() {
        System.out.println("2success 正常请求");
        return "success";
    }
}
