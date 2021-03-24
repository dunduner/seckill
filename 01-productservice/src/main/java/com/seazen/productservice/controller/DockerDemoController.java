package com.seazen.productservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangning
 * @date 2020/6/17
 */

@RestController
@RequestMapping("/demo")
public class DockerDemoController {
    @RequestMapping("/user/find")
    @ResponseBody
    public Object findUser(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("name","xdclass.net");
        map.put("age","28");
        return map;
    }
}
