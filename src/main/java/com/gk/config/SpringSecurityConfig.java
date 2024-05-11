package com.gk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/",
                                        "/auth/**", "/images/**", "/css/**", "/js/**", "/fonts/**"
                                ).permitAll()
                                .requestMatchers("/category/**", "/product/**","/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .successHandler(new MyCustomSuccessHandler())
                        .permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
