package com.seazen.productservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.config.DiscoveryClientOptionalArgsConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/service-instance")
@RestController
public class ServiceInstanceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/query-by-application-name/{applicationName}")
    public List<ServiceInstance> getInstance(@PathVariable("applicationName") String applicationName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(applicationName);
        for (ServiceInstance serviceInstance : instances) {
            Map<String, String> metadata = serviceInstance.getMetadata();
            String username = metadata.get("user.name");
            System.out.println("username的元数据："+username);
            System.out.println("version的元数据："+metadata.get("version"));
        }
        return instances;
    }

}