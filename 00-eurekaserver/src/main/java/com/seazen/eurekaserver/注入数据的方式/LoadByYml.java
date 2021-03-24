package com.seazen.eurekaserver.注入数据的方式;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = {"classpath:application-my.yml"})
@ConfigurationProperties(prefix = "zhangning")
public class LoadByYml {

    @Value("${addr}")
    private String addr; // 注入第一个配置外部文件属性

    @Value("${age}")
    private String age;

}
