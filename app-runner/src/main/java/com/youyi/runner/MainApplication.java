package com.youyi.runner;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@EnableScheduling
@MapperScan(
    basePackages = {
        "com.youyi.infra.conf.repository.mapper",
        "com.youyi.domain.audit.repository.mapper",
    },
    annotationClass = Mapper.class
)
@SpringBootApplication(
    scanBasePackages = {
        "com.youyi.runner",
        "com.youyi.domain",
        "com.youyi.infra"
    }
)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
