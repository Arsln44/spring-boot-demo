package com.mimari.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Bu, bizim "genel" Bean'lerimizi tutan yeni konfigürasyon sınıfımızdır.
@Configuration
public class ApplicationConfig {

    // 1. PasswordEncoder BEAN'İNİ BURAYA TAŞIDIK
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. UserDetailsService BEAN'İNİ BURAYA TAŞIDIK
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin100"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}