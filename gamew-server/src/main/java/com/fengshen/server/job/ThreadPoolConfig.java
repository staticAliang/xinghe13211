package com.fengshen.server.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程池配置类
 * @author:
 * @date: 2020年12月3日 上午11:29:31
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    // 核心线程数大小
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 定时任务线程池
     *
     * @return
     */
    @Bean("taskThreadPool")
    public Executor taskThreadPool() {
        log.info("taskThreadPool init......");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(CORE_SIZE * 4);
        // 配置最大线程数
        executor.setMaxPoolSize(CORE_SIZE * 8);
        // 配置队列大小
        executor.setQueueCapacity(80);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("TaskPool-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
