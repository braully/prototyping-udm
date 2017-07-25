package com.github.braully.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author strike
 */
@SpringBootApplication
@ComponentScan({"com.github.braully"})
@EntityScan(basePackages = "com.github.braully.domain")
@EnableScheduling
@EnableAsync
public class SpringMainConfigFinancePtBr {

    public static void main(String... args) {
        SpringApplication.run(SpringMainConfigFinancePtBr.class, args);
    }

}
