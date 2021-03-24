package com.seazen.zuul.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author zhangning
 * @date 2020/7/27
 */
public final class ExceptionUtil {

    public static String handleException(BlockException ex) {
        System.err.println("限流了了！");
        System.err.println("错误发生: " + ex.getClass().getCanonicalName());
        return "error";
    }

}
