package by.it.academy.adorop.web.security;

import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.web.security.authentication.providers.StudentAuthenticationProvider;
import by.it.academy.adorop.web.security.authentication.providers.TeacherAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("by.it.academy.adorop.web.security")
@Import(ServiceConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    StudentAuthenticationProvider studentAuthenticationProvider;
    @Autowired
    TeacherAuthenticationProvider teacherAuthenticationProvider;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(studentAuthenticationProvider)
                .authorizeRequests()
                .antMatchers("/students/new")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/students/**")
                .authenticated()
            .and()
                .authenticationProvider(teacherAuthenticationProvider)
                .authorizeRequests()
                .antMatchers("/teachers/new")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/teachers/**")
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .successHandler(authenticationSuccessHandler);
    }
}
