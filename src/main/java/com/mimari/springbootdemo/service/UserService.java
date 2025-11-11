package com.mimari.springbootdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// --- YENİ KISIMLAR ---
// 1. @Service: Bu da @Component gibidir, ancak "İş Mantığı Servisi"
//    olduğunu belirtir. Spring bunu da bulup 'Bean' yapacak.
@Service
// 2. @RequiredArgsConstructor: Bu LOMBOK'tan gelir.
//    Aşağıdaki 'final' olarak işaretlenmiş 'notificationService'
//    alanını alıp, bizim 'spring-core-basics' projemizde
//    MANUEL YAZDIĞIMIZ 'public UserService(NotificationService ns) { ... }'
//    constructor'ını OTOMATİK olarak yaratır.
@RequiredArgsConstructor
public class UserService {

    // 3. Bağımlılığı 'final' olarak tanımlıyoruz (Constructor Injection için).
    private final NotificationService notificationService;

    public void registerUser(String username) {
        System.out.println(username+" veritabanına kaydediliyor...");

        notificationService.sendNotification(username);
    }
}
