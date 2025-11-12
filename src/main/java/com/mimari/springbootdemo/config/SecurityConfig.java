package com.mimari.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

                        // H2 Konsolu (ve tüm alt yolları) herkese açık
                        .requestMatchers("/h2-console/**").permitAll()

                        // (YENİ): Spring Boot Hata Sayfası (/error) herkese açık
                        // Bu, 401 hatası yerine GERÇEK 500/404 hatalarını görmemizi sağlar.
                        .requestMatchers("/error").permitAll()

                        // 2. ESKİ YAKALAMA KURALI (Fallback):
                        //    "...dışındaki GERİ KALAN TÜM İSTEKLER (anyRequest)..."
                        .anyRequest().authenticated() // "...Kimlik Doğrulaması (Authenticated) zorunludur."
                )
                // H2 Konsolu (Frame) ayarı
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // --- GÜNCELLEME BİTTİ ---

                .httpBasic(Customizer.withDefaults()); // Kimlik doğrulama tipi hala Basic Auth

        return http.build();
    }

    // --- YENİ EKLENEN BEAN ---
    // Spring IoC Konteynerine bir 'PasswordEncoder' Bean'i ekliyoruz.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt, parolaları hash'lemek için endüstri standardıdır.
        // Spring Security'ye diyoruz ki: "Parola işleme işin
        // olduğunda, bu BCrypt aracını kullan."
        return new BCryptPasswordEncoder();
    }

    // --- YENİ EKLENEN BEAN (ÇATIŞMAYI ÇÖZEN) ---
    // Bu Bean, 'application.properties'teki 'admin/admin123'ün yerini alır.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        // 'admin' kullanıcısını HAFIZADA oluşturuyoruz.
        UserDetails admin = User.builder()
                .username("admin")
        // Yeni parolamız 'admin100' olsun.
        // Bu parolayı 'passwordEncoder' Bean'imizle HASH'liyoruz.
        // Spring artık 'admin100' (düz metin) ile
        // bu HASH'i karşılaştırabilir.
                .password(passwordEncoder.encode("admin100"))
                .roles("ADMIN","USER") // kullanıcıya roller atıyoruz
                .build();

        // Bu hafıza-içi kullanıcı yöneticisini Spring'e Bean olarak veriyoruz.
        return new InMemoryUserDetailsManager(admin);
    }
}
