package by.it.academy.adorop.web.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = setupApplicationContext(servletContext);
        registerDispatcherServlet(servletContext, webApplicationContext);
    }

    private void registerDispatcherServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext webApplicationContext) {
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }

    private AnnotationConfigWebApplicationContext setupApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebConfig.class);
        webApplicationContext.setServletContext(servletContext);
        return webApplicationContext;
    }
}
