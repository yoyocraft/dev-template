package com.youyi.infra.localmsg.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/07/18
 */
// @Configuration
public class LocalMessageConfig {

    @Bean
    public ExecutorService localMessageThreadPool() {
        return Executors.newSingleThreadExecutor();
    }
}
