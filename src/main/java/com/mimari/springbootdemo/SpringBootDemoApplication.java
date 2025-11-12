package com.mimari.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

// --- YENİ ANOTASYON ---
// Spring Boot'a, @Async ile işaretlenmiş metotları
// arka planda ayrı bir iş parçacığında (thread)
// çalıştırma yeteneğini AÇMASINI söylüyoruz
@EnableAsync
// --- BİTTİ ---
@SpringBootApplication
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
