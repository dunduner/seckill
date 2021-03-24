package com.seazen.dynamicroutezuul.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 从配置中心获取配置。
 * config-client-->config-server-->git
 */
@RefreshScope
@RestController
public class ConfigClientAdmin {

    @Value("${from:default}")
    private String from;

    @Autowired
    private Environment environment;


    @RequestMapping("/from")
    public String from() {
        String fromEnv = environment.getProperty("from");
        return from + "_" + fromEnv;
    }
}
