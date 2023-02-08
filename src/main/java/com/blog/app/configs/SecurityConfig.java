package com.blog.app.configs;

import com.blog.app.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/",
            "/posts/**",
            "/categories/**",
            "/authors/**",
            "/signup",
            "/signup-process",
            "/login"
    };

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(
                authorize ->
                    authorize
                        .requestMatchers("/blog-resources/**", "/admin-resources/**").permitAll()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/admin-panel/**").hasAnyRole("ADMIN", "NORMAL")
            )
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/doLogin")
            .defaultSuccessUrl("/admin-panel/dashboard")
            .and()
            .csrf()
            .disable();

        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
