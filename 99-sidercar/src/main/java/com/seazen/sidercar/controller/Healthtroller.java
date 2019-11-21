package com.seazen.sidercar.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Healthtroller {
    @RequestMapping(value = "/health")
    @ResponseBody
    public String health(){
        return "{\"status\":\"up\"}";
    }
}
