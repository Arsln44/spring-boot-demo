package com.mimari.springbootdemo.controller;

import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import com.mimari.springbootdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // --- METOT İMZASI GÜNCELLENDİ ---
    // Artık '@RequestBody String username' DEĞİL,
    // '@RequestBody RegisterRequestDTO request' alıyoruz.
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody RegisterRequestDTO request) {

        // 1. DTO'YU ALMA:
        //    Spring'in 'jackson' kütüphanesi (starter-web ile geldi),
        //    Postman'den gelen JSON'u OTOMATİK olarak bizim
        //    'RegisterRequestDTO' nesnemize dönüştürecek.

        // 2. GÖREVİ DELEGE ETME:
        //    Controller HİÇBİR iş mantığı yapmaz.
        //    Gelen DTO'yu olduğu gibi 'UserService'e paslar.
        userService.registerUser(request);

        // 3. BAŞARI CEVABI:
        //    Cevap olarak artık 'username'i değil,
        //    DTO'dan gelen 'email'i dönelim (daha mantıklı).
        return request.getEmail() + " başarı ile kaydedildi.";
    }
}
