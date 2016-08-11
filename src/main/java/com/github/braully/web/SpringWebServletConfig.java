/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import com.sun.faces.config.FacesInitializer;
import java.util.HashSet;
import java.util.Set;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author braully
 */
@Configuration
public class SpringWebServletConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet(), "/api/*", "*.mvc", "*.thf");
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean
                = new ServletRegistrationBean(new FacesServlet(), "*.jsf") {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                FacesInitializer facesInitializer = new FacesInitializer();

                Set<Class<?>> clazz = new HashSet<>();
                clazz.add(SpringWebConfig.class);
                facesInitializer.onStartup(clazz, servletContext);
            }
        };
//        servletRegistrationBean.addInitParameter("javax.faces.WEBAPP_RESOURCES_DIRECTORY", "jsf");
//        servletRegistrationBean.addInitParameter("javax.faces.DEFAULT_SUFFIX", ".html");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

//    @Bean
//    public ServletContextInitializer initializer() {
//        return (ServletContext servletContext) -> {
////            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".html");
//        };
//    }
}
