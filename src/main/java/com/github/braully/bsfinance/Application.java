/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.bsfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;

/**
 *
 * @author strike
 */
@SpringBootApplication //@Configuration @EnableAutoConfiguration @ComponentScan
@EntityScan(
        basePackages = {
            "com.github.braully.domain"
        }
)
public class Application extends SpringBootServletInitializer {

//    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//        "classpath:/META-INF/resources/", "classpath:/resources/",
//        "classpath:/static/", "classpath:/public/"};
//
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("file:/path/to/my/dropbox/");
//    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
