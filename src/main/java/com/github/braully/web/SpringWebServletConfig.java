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
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author braully
 */
@Configuration
public class SpringWebServletConfig {

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
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}
