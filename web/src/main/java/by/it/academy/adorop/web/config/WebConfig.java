package by.it.academy.adorop.web.config;

import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.web.config.handlers.api.CourseByIdHandlerMethodArgumentResolver;
import by.it.academy.adorop.web.config.handlers.api.MarkByIdHandlerMethodArgumentResolver;
import by.it.academy.adorop.web.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.List;

//TODO LocaleResolver, ExceptionHandler
@Configuration
@EnableWebMvc
@ComponentScan("by.it.academy.adorop.web")
@Import({ServiceConfig.class, SecurityConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    MarkByIdHandlerMethodArgumentResolver markByIdHandlerMethodArgumentResolver;
    @Autowired
    CourseByIdHandlerMethodArgumentResolver courseByIdHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(markByIdHandlerMethodArgumentResolver);
        argumentResolvers.add(courseByIdHandlerMethodArgumentResolver);
    }

    @Bean
    public TilesViewResolver viewResolver() {
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        return tilesConfigurer;
    }
}
