package com.izouir.web.moviego.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) -> {
                    try {
                        auth
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
                    } catch (final Exception exception) {
                        throw new RuntimeException(exception);
                    }
                }).httpBasic(withDefaults());
        return httpSecurity.userDetailsService(userDetailsService).build();
    }

    @Bean
    @Autowired
    public UserDetailsManager userDetailsManager(final DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
