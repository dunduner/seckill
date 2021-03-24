package com.seazen.eurekaserver.bean;

import lombok.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.Min;


@Builder
@Getter
@Data
@AllArgsConstructor
 @NoArgsConstructor
public class Book {
    /**
     * @author lune
     */

    private int id;
    private String name;
    private String author;
    private int year;
    private double price;

    @PostConstruct
    public void init() {
        System.out.println("bean初始化之后执行...");
    }
    @PreDestroy
    public void destory() {
        System.out.println("bean销毁之后执行...");
    }


}