package com.mimari.springbootdemo.service;

import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import com.mimari.springbootdemo.mapper.UserMapper;
import com.mimari.springbootdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    // 1. Mevcut Bağımlılıklar
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 2. YENİ BAĞIMLILIK (MapStruct'ın ürettiği Bean)
    //    Spring, 'UserMapperImpl' sınıfını bulup buraya enjekte edecek.
    private final UserMapper userMapper;

    public void registerUser(RegisterRequestDTO request) {

        // --- BÖLÜM 1: OTOMATİK DÖNÜŞÜM (YENİ YÖNTEM) ---
        // 'UserMapper' arayüzümüzdeki 'dtoToEntity' metodunu çağırıyoruz.
        // MapStruct'ın ürettiği kod, DTO'yu Entity'ye dönüştürür.
        // (name, email alanlarını eşler, 'password'ü 'ignore' eder)
        User userToSave = userMapper.dtoToEntity(request);

        // --- "ANGARYA" KOD SİLİNDİ ---
        // User userToSave = new User();
        // userToSave.setName(request.getName());
        // userToSave.setEmail(request.getEmail());
        // --- "ANGARYA" KOD SİLİNDİ ---


        // --- BÖLÜM 2: MANUEL İŞLEM (Hala Gerekli) ---
        // 'password'ü 'ignore = true' yaptığımız için,
        // hash'leme işini hala GÜVENLİ bir şekilde manuel yapıyoruz.
        // Bu, MapStruct'ın otomasyonunu, mimari güvenlikle
        // birleştirmenin mükemmel bir örneğidir.
        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));

        // --- BÖLÜM 3: KAYIT VE BİLDİRİM (Değişmedi) ---
        User savedUser = userRepository.save(userToSave);
        notificationService.sendNotification(savedUser.getName());
    }
}