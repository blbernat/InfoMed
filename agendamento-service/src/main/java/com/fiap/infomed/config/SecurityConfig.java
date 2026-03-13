package com.fiap.infomed.config;

import com.fiap.infomed.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST, considere habilitar para aplicações web
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/agendamentos/**").hasAnyRole("MEDICO", "ENFERMEIRO")
                        .requestMatchers("/graphql/**").authenticated() // Todos os usuários autenticados podem acessar GraphQL
                        .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
                )
                .httpBasic(org.springframework.security.config.Customizer.withDefaults()); // Habilita autenticação HTTP Basic

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
