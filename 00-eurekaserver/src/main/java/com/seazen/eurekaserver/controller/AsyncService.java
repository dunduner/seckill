package com.seazen.eurekaserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/3/11
 */
@Service
public class AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);
    @Async
    public void testNoRespNoParamAsync() {
        logger.info("多线程执行开始-AsyncService begins to execute!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("AsyncService was interrupted!", e);
            return;
        }

        logger.info("多线程执行完成-AsyncService execution completed!");
    }
}