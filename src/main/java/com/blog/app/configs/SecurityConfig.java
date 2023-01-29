//package com.blog.app.configs;
//
//import com.blog.app.security.CustomUserDetailsService;
//import com.blog.app.security.JwtAuthenticationEntryPoint;
//import com.blog.app.security.JwtAuthenticationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@EnableWebMvc
//public class SecurityConfig {
//
//    public static final String[] PUBLIC_URLS = {
//            "/api/v1/auth/**",
//            "/v3/api-docs/**",
//            "/v2/api-docs",
//            "/swagger-resources/**",
//            "/swagger-ui/**",
//            "/webjars/**"
//    };
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//            .csrf()
//            .disable()
//            .authorizeHttpRequests()
//            .requestMatchers(PUBLIC_URLS).permitAll()
//            .requestMatchers(HttpMethod.GET).permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
