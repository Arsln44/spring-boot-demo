package com.mimari.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // CSRF koruması kapalı (REST API için)

                // --- YETKİLENDİRME (AUTHORIZATION) KURALLARI GÜNCELLENDİ ---
                .authorizeHttpRequests(authz -> authz

                        // 1. YENİ İSTİSNA KURALI:
                        //    "HTTP POST metodu ile '/api/users/register' adresine gelen..."
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        // 2. ESKİ YAKALAMA KURALI (Fallback):
                        //    "...dışındaki GERİ KALAN TÜM İSTEKLER (anyRequest)..."
                        .anyRequest().authenticated() // "...Kimlik Doğrulaması (Authenticated) zorunludur."
                )
                // --- GÜNCELLEME BİTTİ ---

                .httpBasic(Customizer.withDefaults()); // Kimlik doğrulama tipi hala Basic Auth

        return http.build();
    }
}
