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

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        super.onStartup(servletContext);
//        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
//        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
//    }
//
//    @Bean
//    public ServletRegistrationBean facesServlet() {
//        FacesServlet servlet = new FacesServlet();
//        ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.xhtml");
//        registration.setName("FacesServlet");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
