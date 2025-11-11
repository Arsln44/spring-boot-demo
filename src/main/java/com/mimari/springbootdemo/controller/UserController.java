package com.mimari.springbootdemo.controller;

import com.mimari.springbootdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 1. @RestController: Bu, @Controller ve @ResponseBody'nin birleşimidir.
//    Spring'e der ki: "Bu sınıf bir web kapısıdır ve metotlarından dönen
//    değerleri (örn: String) doğrudan HTTP Cevabına (Response) yaz."
//    (Yani JSON'a çevir veya düz metin gönder, bir HTML sayfası arama).
@RestController

// 2. @RequestMapping: Bu sınıftaki TÜM "kapıların" başına
//    '/api/users' önekini ekle.
//    Örn: http://localhost:8080/api/users/.
@RequestMapping("/api/users")

// 3. @RequiredArgsConstructor: Tıpkı ana sınıfta yaptığımız gibi,
//    constructor enjeksiyonu için Lombok'u kullanıyoruz.
@RequiredArgsConstructor
public class UserController {

    // 4. BAĞIMLILIĞI ENJEKTE ETME:
    //    Bu Controller'ın, iş mantığını çalıştırmak için UserService'e
    //    ihtiyacı var. Spring, @Service ile işaretli UserService'i bulup
    //    buraya OTOMATİK olarak enjekte edecek.
    private final UserService userService;

    // 5. YENİ "KAPI" (ENDPOINT) OLUŞTURMA:
    //    @PostMapping: Bu metodu, HTTP POST isteklerine bağla.
    //    "/register" : Bu metodun adresini /api/users/register yap.
    @PostMapping("/register")
    //    @ResponseStatus: Bu işlem başarılı olursa,
    //    HTTP 201 (CREATED) durum kodunu döndür.
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody String username) {
        // 6. İŞ MANTIĞINI TETİKLEME:
        //    HTTP isteğinin gövdesinden (@RequestBody) gelen 'username'
        //    verisini al ve 'userService'e pasla.
        userService.registerUser(username);

        // 7. CEVAP (RESPONSE) DÖNDÜRME:
        //    İsteği yapana (örn: mobil uygulama) bir onay mesajı döndür.
        //    @RestController sayesinde bu String, JSON veya düz metin olarak döner.
        return username + " başarıyla kaydedildi.";
    }
}
