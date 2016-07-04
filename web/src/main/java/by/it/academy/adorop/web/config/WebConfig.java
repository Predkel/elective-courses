package by.it.academy.adorop.web.config;

import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.web.security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

//TODO LocaleResolver, ExceptionHandler
@Configuration
@EnableWebMvc
@ComponentScan("by.it.academy.adorop.web.controllers")
@Import({ServiceConfig.class, SecurityConfig.class})
public class WebConfig {
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
