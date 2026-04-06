package com.zorvyn.finance_dashboard_system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigurations {

    private final JwtAuthrizationFilter jwtAuthrizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //Public
                        .requestMatchers("/auth/**").permitAll()

                        //Viewer (basic Acess)
                        .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "ANALYST", "CLIENT")

                        .requestMatchers("/records/**").hasAnyRole("ADMIN", "ANALYST", "CLIENT")

                        //Analyst
                        .requestMatchers("/insights/**").hasAnyRole("ADMIN", "ANALYST")

                        //Admin
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")


                        //Default
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthrizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

// Flow - Request → JwtAuthrizationFilter → Extract Token → Validate → Set Authentication → Controller