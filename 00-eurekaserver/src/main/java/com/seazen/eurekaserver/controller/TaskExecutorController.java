package com.seazen.eurekaserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/task")
public class TaskExecutorController {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorController.class);
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AsyncService asyncService;


    //http://127.0.0.1:8761/task/testTaskExecutor
    @RequestMapping("/testTaskExecutor")
    public String testTaskExecutor() {
        logger.info("主线程执行TestTaskExecutor function begin to execute!");

        taskExecutor.execute(() -> {
            logger.info("多线程执行开始Real thread begin to execute! threadName={}", Thread.currentThread().getName());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Real thread was interrupted!", e);
                return;
            }

            logger.info("多线程执行结束Real thread has been executed!线程名={}", Thread.currentThread().getName());
        });

        logger.info("主线程直接结束！TestTaskExecutor function has been executed!");

        return "Succeed!";
    }

    @RequestMapping("/testAsync")
    public String testAsync() {
        logger.info("主线程开始---TestAsync begins to execute!");
        long startTime = System.currentTimeMillis();

        asyncService.testNoRespNoParamAsync();
        logger.info("主线程执行结束-TestAsync execution completed, use time: {}!", (System.currentTimeMillis() - startTime) / 1000);

        return "End!";
    }
}
