package com.github.braully.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .addResourceLocations("classpath*:META-INF/resources/**");
    }

    @Bean
    public ViewResolver internalResourceViewResolverJsp() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/mvc/");
        resolver.setViewClass(JstlView.class);
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }
}

//    private ApplicationContext applicationContext;
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean servletRegistrationBean
//                = new ServletRegistrationBean(new FacesServlet(), "*.jsf") {
//            @Override
//            public void onStartup(ServletContext servletContext) throws ServletException {
//                FacesInitializer facesInitializer = new FacesInitializer();
//                Set<Class<?>> clazz = new HashSet<>();
//                clazz.add(SpringWebConfig.class);
//                facesInitializer.onStartup(clazz, servletContext);
//            }
//        };
//        servletRegistrationBean.setLoadOnStartup(1);
//        return servletRegistrationBean;
//    }
//    @Bean
//    public ViewResolver viewResolverFacelet() {
//        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//        resolver.setViewClass(SpringFaceletView.class);
//        resolver.setPrefix("/jsf/");
//        resolver.setSuffix(".html");
//        resolver.setCache(false);
//        resolver.setOrder(0);
//        return resolver;
//    }
//
//    @Bean
//    public ViewResolver viewResolverThymeleaf() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setOrder(1);
//        return resolver;
//    }
//
//    @Bean
//    public InternalResourceViewResolver viewResolverJsp() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/mvc/");
//        viewResolver.setSuffix(".jsp");
//        viewResolver.setOrder(2);
//        return viewResolver;
//    }
//
//    @Bean
//    public ViewResolver viewResolverDefault() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////        resolver.setPrefix("/");
////        resolver.setSuffix(".html");
//        resolver.setOrder(3);
//        return resolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Bean
//    public TemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setEnableSpringELCompiler(true);
////        org.springframework.web.jsf.el.SpringBeanFacesELResolver t;
//        engine.setTemplateResolver(templateResolver());
//        return engine;
//    }
//
//    private ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        resolver.setPrefix("/thf/");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/assets/**").setCachePeriod(3600 * 24)
//                .addResourceLocations("/js/", "/css/", "i18n", "/templates/", "/bower_components/");
//    }
//@ComponentScan({"com.github.braully"})
//@ImportResource("classpath:config.xml")
