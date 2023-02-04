package com.blog.app.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        authorize ->
                                authorize.requestMatchers("/admin-panel/**").permitAll()
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .csrf();

        return httpSecurity.build();
    }
}
