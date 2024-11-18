package com.example.kanban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desativa CSRF para facilitar os testes via Postman
                .authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll() // Permite acesso público aos endpoints da API
                .requestMatchers("/h2-console/**").permitAll() // Permite acesso público ao H2 Console
                .anyRequest().authenticated() // Outros endpoints exigem autenticação
                .and()
                .formLogin().disable() // Remove o formulário de login
                .headers().frameOptions().disable(); // Permite o uso de iframe para o H2 Console

        return http.build();
    }
}