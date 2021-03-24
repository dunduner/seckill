package com.seazen.dynamicroutezuul.errorhandler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
* 出异常后进入该方法，交由下面的方法处理
* 例如：404  500等
* */
@RestController
public class ErrorHandlerController implements ErrorController {

    // 出异常后进入该方法，交由下面的方法处理
    @Override
    public String getErrorPath() {
        return "/error";//跳转到这个地方的地址
    }

    @RequestMapping("/error")
    public String error() {
        return "出现异常---ErrorHandlerController：18行";
    }

}