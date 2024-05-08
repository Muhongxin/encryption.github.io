package com.mhx.encryption.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className AsyncConfiguration
 * @description 线程池配置文件
 * @author MuHongXin.
 * @date 2023/05/31 15:20
 * @version v1.0.0
 **/

@Configuration
public class AsyncConfiguration {
    /**
    * @Description: 加密服务线程池
    *               Encrypt the service thread pool
    * @Author: MuHongXin
    * @DateTime: 下午4:46 2022/11/8
    * @Return: Executor
    */
    @Bean("encryptionExecutor")
    public Executor logExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心池大小 Core * 2 (SSD +1)
        executor.setCorePoolSize(5);
        // 最大线程数 CorePoolSize * 4
        executor.setMaxPoolSize(20);
        // 队列程度 CorePoolSize * 10
        executor.setQueueCapacity(50);
        // 线程空闲时间 x秒
        executor.setKeepAliveSeconds(1000);
        // 线程前缀名称 方便log查询定位
        executor.setThreadNamePrefix("encryption-async");
        // 配置拒绝策略 线程池拒绝服务后由当前线程运行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
