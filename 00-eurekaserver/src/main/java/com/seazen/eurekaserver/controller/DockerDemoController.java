package com.seazen.eurekaserver.controller;

import com.seazen.eurekaserver.注入数据的方式.LoadByProperties;
import com.seazen.eurekaserver.注入数据的方式.LoadByYml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangning
 * @date 2020/6/17
 */

@Controller
public class DockerDemoController {

    @Autowired
    private LoadByProperties loadPropsTest;

    @Autowired
    private LoadByYml loadYML;

    @RequestMapping(value = "/user/find", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, String> findUser() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "zhangning.java");
        map.put("age", "18");
        System.out.println(loadPropsTest.getBookName());
        System.out.println(loadPropsTest.getAuthor());
        System.out.println(loadYML.getAddr());
        System.out.println(loadYML.getAge());
        return map;
    }
}
