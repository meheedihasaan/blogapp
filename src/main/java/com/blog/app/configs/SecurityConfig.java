//package com.blog.app.configs;
//
//import com.blog.app.security.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@EnableWebMvc
//public class SecurityConfig {
//
//    public static final String[] PUBLIC_URLS = {
//            "/",
//            "/posts/**",
//            "/categories/**",
//            "/authors/**",
//            "/signup",
//            "/login"
//    };
//
//    public static final String[] STATIC_RESOURCES = {"/css/**", "/images/**", "/js/**", "/assets/**", "/scss/**", "/fonts/**", "/flaicon/**", "/font/**",
//            "/resources/**", "/blog-resources/**", "/admin-resources/**", "/api/**", "/", "/static/**", "/blog-resources/css/**",
//            "/blog-resources/js/**", "/blog-resources/imgages/**", "/blog-resources/fonts/**", "/admin-resources/assets/demo/**",
//            "/admin-resources/css/**", "/admin-resources/js/**"
//    };
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//            .csrf()
//            .disable()
//            .authorizeHttpRequests()
//            .requestMatchers(STATIC_RESOURCES).permitAll()
//            .requestMatchers(PUBLIC_URLS).permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .defaultSuccessUrl("/admin-template/dashboard");
//
//
//        httpSecurity.authenticationProvider(daoAuthenticationProvider());
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//}
