package com.seazen.eurekaserver;

import com.seazen.eurekaserver.bean.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

//---spring.profiles.active=discovery3
@EnableEurekaServer//服务发现服务器端
@SpringBootApplication
@EnableAsync
public class EurekaServerApplication {

    //测试优雅关机
    //post http://localhost:8761/actuator/shutdown
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @Bean
    public Book getBook(){
        return  new Book();
    }

}

