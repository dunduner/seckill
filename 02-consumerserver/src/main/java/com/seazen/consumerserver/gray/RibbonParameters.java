package com.seazen.consumerserver.gray;

import org.springframework.stereotype.Component;

/**
 * @author zhangning
 * @date 2020/7/16
 */

@Component
public class RibbonParameters {

    private static  final  ThreadLocal LOCAL = new ThreadLocal();


    // get
    public static <T> T get(){
        return  (T)LOCAL.get();
    }

    // set
    public static <T> void set(T t){
        LOCAL.set(t);
    }
}
