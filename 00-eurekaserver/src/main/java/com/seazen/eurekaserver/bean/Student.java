package com.seazen.eurekaserver.bean;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * 从yml中获取配置属性
 */
@Data
@Component(value = "st")
@ConfigurationProperties(prefix="student",ignoreInvalidFields=true, ignoreUnknownFields=true)
public class Student {

    private String name;
    private Integer age;
    private Map<String,Integer> score  = new HashMap<String,Integer>();


}
