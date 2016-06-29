package by.it.academy.adorop.web.config;

import by.it.academy.adorop.dao.config.PersistenceConfig;
import by.it.academy.adorop.service.config.ServiceConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
//TODO refactoring
public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
//TODO?
        webApplicationContext.register(WebConfig.class, ServiceConfig.class);
        webApplicationContext.setServletContext(servletContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));

        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
