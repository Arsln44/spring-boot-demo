package com.mimari.springbootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import com.mimari.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 1. @SpringBootTest: Bu, "Birim Testi" DEĞİLDİR.
//    Bu, JUnit 5'e der ki: "TÜM Spring Boot uygulamasını
//    (H2, Security, Service, her şeyi) AYAĞA KALDIR."
//    Bu test yavaştır (birkaç saniye) ama gerçektir.
@SpringBootTest

// 2. @AutoConfigureMockMvc: Spring'e, Postman'i simüle edebilmemiz için
//    bize sahte bir "HTTP İstek Yöneticisi" (MockMvc) Bean'i vermesini söyler.
@AutoConfigureMockMvc
class SpringBootDemoApplicationTests {

    // 3. BAĞIMLILIKLARI ENJEKTE ETME (@Autowired)
    //    Burada @Mock KULLANMIYORUZ.
    //    Spring'in ayağa kaldırdığı GERÇEK Bean'leri istiyoruz.

    @Autowired
    private MockMvc mockMvc; // Postman'i simüle edecek araç

    @Autowired
    private UserRepository userRepository; // Gerçek H2 veritabanını kontrol etmek için

    @Autowired
    private ObjectMapper objectMapper; // DTO'muzu JSON'a çevirmek için

    @Autowired
    private PasswordEncoder passwordEncoder; // Parolanın hash'lendiğini doğrulamak için

    // 4. @Test: Bu bizim entegrasyon testimiz.
    @Test
    void registerUser_whenRequestIsValid_shouldCreateUserAndReturn201() throws Exception {

        // --- BÖLÜM 1: ARRANGE (KURULUM) ---
        // Postman'in "Body" sekmesinde yaptığımız gibi,
        // göndereceğimiz DTO'yu (ve JSON'u) hazırlıyoruz.
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setName("Entegrasyon Testi");
        requestDTO.setEmail("entegrasyon@test.com");
        requestDTO.setPassword("guvenliSifre123");

        // DTO'yu JSON String'ine çevir
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        // --- BÖLÜM 2: ACT (EYLEM) ---
        // 'mockMvc' ile Postman'in "Send" butonuna basmasını simüle ediyoruz.
        // Bu, GERÇEK bir HTTP isteği GİBİ davranır,
        // GERÇEK SecurityConfig'den geçer,
        // GERÇEK UserController'a ulaşır.
        mockMvc.perform(
                // Metot: POST
                post("/api/users/register")
                // Header: Content-Type = application/json
                        .contentType(MediaType.APPLICATION_JSON)
                // Body: Hazırladığımız JSON
                        .content(requestJson)
        )
        // --- BÖLÜM 3: ASSERT (DOĞRULAMA) ---
        // "Sonuç ne oldu?"

        // KONTROL 1 (HTTP CEVABI):
        // Durum kodunun '201 Created' olmasını BEKLİYORUM.
                .andExpect(status().isCreated())
                // Cevap gövdesinin '...başarıyla kaydedildi' olmasını bekliyorum
                .andExpect(content().string("entegrasyon@test.com başarı ile kaydedildi."));

        // KONTROL 2 (VERİTABANI DOĞRULAMASI - En Kritik Kısım):
        // HTTP cevabı GÜZEL, peki H2'ye GERÇEKTEN kaydedildi mi?
        // GERÇEK repository'yi kullanarak veritabanını sorguluyoruz
        User userInDb = userRepository.findAll().get(0);

        assertThat(userInDb).isNotNull();
        assertThat(userInDb.getEmail()).isEqualTo("entegrasyon@test.com");

        // KONTROL 3 (GÜVENLİK DOĞRULAMASI):
        // Parolamız HASH'lendi mi?
        assertThat(userInDb.getPassword()).isNotEqualTo("guvenliSifre123");
        assertThat(passwordEncoder.matches("guvenliSifre123", userInDb.getPassword())).isTrue();
    }

}
