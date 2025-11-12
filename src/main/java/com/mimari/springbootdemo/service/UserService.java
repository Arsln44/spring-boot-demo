package com.mimari.springbootdemo.service;

import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import com.mimari.springbootdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    // 1. ESKİ BAĞIMLILIK (Hala geçerli)
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    // 3. YENİ BAĞIMLILIK: PASSWORD ENCODER
    //    SecurityConfig'de yarattığımız 'passwordEncoder' Bean'ini
    //    buraya enjekte ediyoruz.
    private final PasswordEncoder passwordEncoder;

    // --- METOT İMZASI GÜNCELLENDİ ---
    // Artık 'String username' DEĞİL, 'RegisterRequestDTO request' alıyor.
    public void registerUser(RegisterRequestDTO request) {

        // --- MİMARİ GÜVENLİK ADIMI (DTO -> ENTITY MAPPING) ---
        // Bu, "Mass Assignment" saldırısını engellediğimiz yerdir.
        // DTO'dan Entity'ye manuel ve GÜVENLİ dönüştürme yapıyoruz.
        User userToSave = new User();

        // 1. Sadece GÜVENDİĞİMİZ alanları DTO'dan Entity'ye aktarırız:
        userToSave.setName(request.getName());
        userToSave.setEmail(request.getEmail());

        // 2. PAROLA HASHLEME:
        //    DTO'dan gelen DÜZ METİN parolayı ASLA kaydetmeyiz.
        //    'passwordEncoder' kullanarak onu HASH'leriz (örn: $2a$10$...).
        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));

        // 'isAdmin' gibi bir alan DTO'da olsa bile, onu buraya
        // aktarmadığımız için ASLA veritabanına gidemez.
        // --- GÜVENLİK ADIMI TAMAMLANDI ---

        // 3. GERÇEK KAYIT İŞLEMİ:
        //    Artık GÜVENLİ olan 'userToSave' Entity'mizi kaydediyoruz
        User savedUser = userRepository.save(userToSave);

        // 4. BİLDİRİM GÖNDERME:
        //    Artık 'username' (String) yok. Kaydedilen kullanıcının
        //    ismini (getName()) kullanarak bildirim gönderiyoruz.
        notificationService.sendNotification(savedUser.getName());
    }
}