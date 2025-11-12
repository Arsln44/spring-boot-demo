// Dosya: src/main/java/com/mimari/springbootdemo/config/SecurityConfig.java
package com.mimari.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// Bu sınıfın artık TEK BİR SORUMLULUĞU var: Güvenlik Duvarını (FilterChain) tanımlamak.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // --- YETKİLENDİRME KURALLARI (SWAGGER EKLENDİ) ---
                .authorizeHttpRequests(authz -> authz

                        // KURAL 1: Kayıt olma
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        // KURAL 2: H2 Konsolu
                        .requestMatchers("/h2-console/**").permitAll()

                        // KURAL 3: Hata Sayfası
                        .requestMatchers("/error").permitAll()

                        // KURAL 4 (YENİ): OpenAPI Dokümantasyonu Kapıları
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // KURAL 5 (Fallback): Geri kalan her şey kilitli
                        .anyRequest().authenticated()
                )
                // --- BİTTİ ---

                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // PasswordEncoder BEAN'İ BURADAN SİLİNDİ
    // UserDetailsService BEAN'İ BURADAN SİLİNDİ
}