package com.github.braully.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.github.braully"})
@EntityScan(basePackages = "com.github.braully.domain")
public class SpringMainConfig {

    public static void main(String... args) {
        SpringApplication.run(SpringMainConfig.class, args);
    }
}
