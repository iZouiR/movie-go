package com.izouir.web.moviego.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/", "/login", "/register", "/movie/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, a.authority " +
                        "FROM users u " +
                        "JOIN users_authorities ua ON (u.id = ua.user_id) " +
                        "JOIN authorities a ON (ua.authority_id = a.id) " +
                        "WHERE username = ?");
    }
}