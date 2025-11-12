package com.mimari.springbootdemo.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

// --- YENİ KISIM BURASI ---
// Spring'e diyoruz ki: "Bu bir 'Komponent'tir".
// Spring Boot, `@ComponentScan` sayesinde bu sınıfı OTOMATİK olarak bulacak,
// bir 'new SmsNotificationService()' nesnesi yaratacak ve konteynere 'Bean' olarak ekleyecek.
@Component
public class SmsNotificationService implements NotificationService {

    // --- YENİ ANOTASYON ---
    // Spring, @EnableAsync açık olduğu için, bu metodu
    // ana iş parçacığında DEĞİL, arka plandaki
    // ayrı bir iş parçacığında (background thread) çalıştıracak.
    @Async
    // --- BİTTİ ---
    @Override
    public void sendNotification(String user) {

        // --- YAVAŞLIĞI SİMÜLE ETME ---
        // Gerçek bir SMS API çağrısının yavaşlığını (network gecikmesi)
        // simüle etmek için thread'i 2 saniye (2000ms) uyutalım.
        try {
            System.out.println(">>> ASYNC: SMS gönderimi başlıyor... (Thread: " + Thread.currentThread().getName() + ")");
            Thread.sleep(2000); // 2 saniyelik sahte gecikme
            System.out.println(">>> ASYNC: SMS gönderildi: " + user + " başarıyla kayıt oldu.");
        } catch (InterruptedException e) {
            System.err.println(">>> ASYNC: SMS gönderimi kesintiye uğradı.");
        }
        // --- SİMÜLASYON BİTTİ ---
    }
}
