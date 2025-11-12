package com.mimari.springbootdemo.domain;

// --- JPA (Java Persistence API) ANNOTATIONLARI ---

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor // JPA, nesneleri yaratmak için boş bir constructor'a ihtiyaç duyar.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'name' alanı 'nullable = false' (boş olamaz) olarak güncellendi
    @Column(nullable = false)
    private String name;

    // YENİ ALAN: Email
    // 'unique = true' : Veritabanı seviyesinde aynı email ile
    // ikinci bir kayda izin verme (mimari kural)
    @Column(nullable = false, unique = true)
    private String email;

    // YENİ ALAN: Parola
    // Bu, 'admin123' gibi düz metin DEĞİL,
    // ' $2a$10$...' gibi HASH'lenmiş parolayı tutacak.
    @Column(nullable = false)
    private String password;

    // Constructor'ı kaldırıyoruz, çünkü DTO'dan manuel mapping
    // (elle dönüştürme) yapacağız, bu daha güvenlidir.
}
