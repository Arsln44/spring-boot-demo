package com.mimari.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 1. @Configuration: Bu sınıfın bir Spring "Konfigürasyon" sınıfı olduğunu belirtir
//    (Tıpkı 'spring-core-basics' projemizdeki AppConfig gibi).
@Configuration

// 2. @EnableWebSecurity: Spring Security'nin varsayılan "sihrini" (AutoConfiguration)
//    devre dışı bırakır ve "Artık benim kurallarım geçerli" dememizi sağlar.
@EnableWebSecurity
public class SecurityConfig {

    // 3. @Bean: Spring'e 'SecurityFilterChain' adında bir Bean ürettiğimizi söylüyoruz.
    //    Bu Bean, projedeki TÜM güvenlik kurallarını tanımlayan "Güvenlik Duvarı"dır.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        // 4. CSRF'İ DEVRE DIŞI BIRAKMA (Bizim sorunumuzun çözümü):
        //    REST API'ler için CSRF'i devre dışı bırakıyoruz.
                .csrf(csrf -> csrf.disable())

        // 5. YETKİLENDİRME KURALLARI (Authorization):
        //    "Gelen BÜTÜN isteklere..."
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated() // "...Kimlik Doğrulaması (Authentication) zorunludur."
                )

        // 6. KİMLİK DOĞRULAMA TİPİ (Authentication):
        //    "Kimlik doğrulaması için HTTP Basic Auth kullan."
                .httpBasic(Customizer.withDefaults());

        // 7. Ayarlanan kuralları 'build' et ve döndür.
        return http.build();
    }
}
