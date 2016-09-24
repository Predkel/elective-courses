package by.it.academy.adorop.web.infrastructure;

import by.it.academy.adorop.service.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("by.it.academy.adorop.web")
@Import({ServiceConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    @Qualifier("modelByIdHandlerArgumentResolver")
    HandlerMethodArgumentResolver modelByIdHandlerArgumentResolver;
//    @Autowired
//    @Qualifier("restrictionsAnnotationHandlerMethodArgumentResolver")
//    HandlerMethodArgumentResolver restrictionsAnnotationHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(modelByIdHandlerArgumentResolver);
//        argumentResolvers.add(restrictionsAnnotationHandlerMethodArgumentResolver);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
    }

    @Bean
    public ViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }
}
