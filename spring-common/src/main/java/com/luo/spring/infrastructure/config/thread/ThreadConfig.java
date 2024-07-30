package com.luo.spring.infrastructure.config.thread;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {
    /**
     * spring使用Async时使用虚拟线程
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(){
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
    /**
     * tomcat使用虚拟线程
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
