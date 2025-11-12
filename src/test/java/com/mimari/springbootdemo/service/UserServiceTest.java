package com.mimari.springbootdemo.service;

import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import com.mimari.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// 1. @ExtendWith: JUnit 5'e, Mockito'nun
//    anotasyonlarını (@Mock, @InjectMocks) etkinleştirmesini söyler.
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 2. @Mock: Bu bağımlılıkların "SAHTE" (Mock) versiyonlarını yarat.
    //    Bu test çalıştığında, H2 veritabanı BAŞLAMAZ.
    //    Bu "sahte" repository hiçbir SQL çalıştırmaz.
    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    // 3. @InjectMocks: Bu, bizim test ettiğimiz "GERÇEK" sınıftır.
    //    Mockito, bu GERÇEK UserService'i yaratır ve
    //    yukarıdaki 3 SAHTE (@Mock) bağımlılığı
    //    onun constructor'ına OTOMATİK enjekte eder.
    @InjectMocks
    private UserService userService;


    // 4. @Test: Bu bir test metodudur.
    @Test
    void registerUser_whenValidRequest_shouldSaveUserAndSendNotification() {

        // --- BÖLÜM 1: ARRANGE (KURULUM) ---
        // Testimiz için sahte veriler ve sahte "kurallar" hazırlarız.

        // 1a. Sahte DTO (Girdi)
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password123");

        // 1b. Sahte Kaydedilmiş User (Çıktı beklentisi)
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");

        // 1c. Sahte Davranış Kuralları (Mocking)
        // KURAL 1: "Ne zaman 'passwordEncoder.encode' metodu 'password123'
        //           ile çağrılırsa, 'hashedPassword' string'ini döndür."
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // KURAL 2: "Ne zaman 'userRepository.save' metodu 'User' tipinde
        //           HERHANGİ BİR nesne (any) ile çağrılırsa,
        //           bizim 'savedUser' nesnemizi döndür."
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // KURAL 3: 'notificationService.sendNotification' için kurala gerek yok,
        //          çünkü o 'void' (bir şey döndürmüyor).


        // --- BÖLÜM 2: ACT (EYLEM) ---
        // Test ettiğimiz asıl metodu çağırırız.
        userService.registerUser(request);


        // --- BÖLÜM 3: ASSERT/VERIFY (DOĞRULAMA) ---
        // "Sözleşmenin" yerine getirilip getirilmediğini kontrol ederiz.

        // KONTROL 1: 'passwordEncoder.encode' metodu,
        //            'password123' parametresiyle TAM 1 KEZ (times(1))
        //            çağırıldı mı?
        verify(passwordEncoder, times(1)).encode("password123");

        // KONTROL 2: 'userRepository.save' metodu,
        //            'User' tipinde HERHANGİ BİR nesne ile
        //            TAM 1 KEZ çağırıldı mı?
        verify(userRepository, times(1)).save(any(User.class));

        // KONTROL 3: 'notificationService.sendNotification' metodu,
        //            'savedUser'dan gelen 'Test User' ismiyle
        //            TAM 1 KEZ çağırıldı mı?
        verify(notificationService, times(1)).sendNotification("Test User");
    }
}