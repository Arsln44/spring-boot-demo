package com.mimari.springbootdemo;

import com.mimari.springbootdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 1. Lombok'u, 'final' alanlar için constructor enjeksiyonu
//    yapması amacıyla tekrar kullanıyoruz.
@RequiredArgsConstructor
public class SpringBootDemoApplication implements CommandLineRunner {

    // 2. Tıpkı UserService'in NotificationService'i istediği gibi,
    //    biz de ana sınıfımızda UserService'i istiyoruz.
    //    Spring, @Service ile işaretlenmiş UserService'i bulup buraya
    //    OTOMATİK OLARAK enjekte edecek.
    private final UserService userService;

    // 3. Bu, bizim ana başlatma metodumuz.
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
        // Bu satır bittiğinde, Spring Boot sunucuyu başlatır
        // ve CommandLineRunner'ları arar.
    }

    // 4. BU, CommandLineRunner ARAYÜZÜNDEN GELEN METOT.
    //    Spring Boot, uygulama başladığında BU METODU otomatik olarak çağırır.
    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- CommandLineRunner BAŞLADI ---");
        System.out.println("Tüm Bean'ler yüklendi. İş mantığı çalıştırılıyor.");

        // 5. 'new' YOK, 'getBean' YOK.
        //    'userService' alanı (Madde 2) Spring tarafından zaten dolduruldu.
        //    Doğrudan kullanıma hazır.
        userService.registerUser("Yunus Emre İzmir");
        System.out.println("--- CommandLineRunner BİTTİ ---");
    }

}
