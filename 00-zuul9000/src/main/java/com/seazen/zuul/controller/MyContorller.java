package com.seazen.zuul.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.netflix.discovery.converters.Auto;
import com.seazen.zuul.exception.ExceptionUtil;
import com.seazen.zuul.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangning
 * @date 2020/7/27
 */

@RestController
public class MyContorller {
    @Autowired
    private SentinelService sentinelService;


    @GetMapping("/myContorller")

    public String my() {
        return sentinelService.success();
    }
}
