package org.kalashnyk.homebudget.config;


import org.kalashnyk.homebudget.service.UserServiceImpl;
import org.kalashnyk.homebudget.util.PasswordUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Created by Sergii on 15.02.2017.
 */
@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated() //all requests will checked
                .and()
                .formLogin().permitAll().usernameParameter("j_username")
                .passwordParameter("j_password").loginProcessingUrl("/j_spring_security_check").failureUrl("/login.html?error=true")
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .and()
                .logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/")
                .and()
                .rememberMe().key("myKey").tokenValiditySeconds(300)
                .and()
                .csrf().disable();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(PasswordUtil.getPasswordEncoder());
        authenticationProvider.setUserDetailsService(/*userDetailsService()*/ userDetailsService());
        return authenticationProvider;
    }
}