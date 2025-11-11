package com.mimari.springbootdemo.service;


import org.springframework.stereotype.Component;

// --- YENİ KISIM BURASI ---
// Spring'e diyoruz ki: "Bu bir 'Komponent'tir".
// Spring Boot, `@ComponentScan` sayesinde bu sınıfı OTOMATİK olarak bulacak,
// bir 'new SmsNotificationService()' nesnesi yaratacak ve konteynere 'Bean' olarak ekleyecek.
@Component
public class SmsNotificationService implements NotificationService {

    @Override
    public void sendNotification(String user) {
        System.out.println("SMS ile bildirim gönderiliyor: "+user+" başarıyla kayıt oldu.");
    }
}
