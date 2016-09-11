package by.it.academy.adorop.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    public void setupAuthenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/students")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/teachers")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/students/current")
                .hasRole("STUDENT")
            .and()
                .authorizeRequests()
                .antMatchers("/teachers/current")
                .hasRole("TEACHER")
            .and()
                .authorizeRequests()
                .antMatchers("/**")
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
