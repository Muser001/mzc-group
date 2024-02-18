package com.model.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService customExecutorService() {
        return new CustomThreadPoolExecutor(
                5, // 核心线程数
                10, // 最大线程数
                60L, // 空闲线程存活时间（秒）
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>()); // 工作队列
    }
}
