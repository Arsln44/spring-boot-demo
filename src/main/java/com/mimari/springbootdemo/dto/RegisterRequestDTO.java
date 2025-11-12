package com.mimari.springbootdemo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Bu bir DTO (Veri Aktarım Nesnesi)
// SADECE veri taşır.
// HİÇBİR @Entity, @Id, @Column gibi VERİTABANI ANNOTATION'I İÇERMEZ.
// Bu, bizim 'Controller' ve 'Entity' arasındaki GÜVENLİK DUVARIMIZDIR.
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {

    // Dış dünyadan (Postman/Mobil App) ALMAYI kabul ettiğimiz
    // GÜVENLİ alanlar.
    private String name;
    private String email;
    private String password;

    // NOT: 'isAdmin', 'id' gibi tehlikeli alanlar
    // burada KASITLI olarak yoktur.
}
