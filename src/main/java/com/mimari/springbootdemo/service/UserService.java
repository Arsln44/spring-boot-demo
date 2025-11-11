package com.mimari.springbootdemo.service;

import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    // 1. ESKİ BAĞIMLILIK (Hala geçerli)
    private final NotificationService notificationService;

    // 2. YENİ BAĞIMLILIK (Veri Katmanı)
    //    Lombok, bunu gördüğü an constructor'a bunu da ekler.
    //    Spring Boot, JpaRepository'nin somut uygulamasını bulup
    //    buraya OTOMATİK enjekte eder.
    private final UserRepository userRepository;

    // 3. 'registerUser' METODUNU GÜNCELLEME
    public void registerUser(String username) {

        // 4. "YALAN" SİLİNDİ
        // System.out.println(username + " veritabanına kaydediliyor..."); // <-- SİLDİK

        // 5. "GERÇEK" GELDİ (Aşama 3A: Nesneyi Yarat)
        //    Gelen 'username' (String) verisinden bir 'User' (Entity) nesnesi yaratıyoruz.
        //    (Constructor'da 'username'i 'name' alanına atamıştık)
        User userToSave = new User(username);

        // 6. "GERÇEK" GELDİ (Aşama 3B: Kaydet)
        //    Enjekte edilen repository'nin 'save' metodunu çağırıyoruz.
        //    Spring Data JPA, bu komutu arka planda H2 veritabanı için
        //    'INSERT INTO users (name) VALUES (?)' SQL'ine çevirir.
        userRepository.save(userToSave);

        // 7. İŞ AKIŞI DEVAM EDİYOR
        //    Kayıt işlemi BAŞARILI olduktan sonra (hata fırlatmazsa)
        //    bildirim gönderiyoruz.
        notificationService.sendNotification(username);
    }
}
