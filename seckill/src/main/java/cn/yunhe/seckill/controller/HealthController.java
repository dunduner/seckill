package cn.yunhe.seckill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


//http://localhost:8080/seckill/health
@Controller
public class HealthController {
    @RequestMapping("/health")
    @ResponseBody
    public Map<String, Object> status(){
        Map<String, Object> map = new HashMap();
        //@ResponseBody将map转为json
        map.put("status", "UP");
        return map;
    }
}
