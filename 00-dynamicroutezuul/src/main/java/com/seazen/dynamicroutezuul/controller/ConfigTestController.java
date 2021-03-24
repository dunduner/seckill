package com.seazen.dynamicroutezuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/configtest1")
public class ConfigTestController {
    @Value("${server.port}")
    public int appId;

    @Value("${webconfig.nname}")
    public String webconfigName;

    @RequestMapping("/config/get")
    public String get() {
        return "端口号是："+this.appId +",webconfigName："+this.webconfigName;
    }
}
