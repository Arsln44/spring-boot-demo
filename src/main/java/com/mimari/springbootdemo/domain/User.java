package com.mimari.springbootdemo.domain;

// --- JPA (Java Persistence API) ANNOTATIONLARI ---

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 1. @Entity: Spring Data JPA'ya bu sınıfın bir veritabanı
//    tablosuna eşlenmesi (map) gerektiğini söyler.
@Entity
// 2. @Table: (Opsiyonel ama iyi pratiktir) Tablonun adını
//    belirler. 'user' SQL'de rezerve bir kelime olabileceğinden
//    'users' (çoğul) kullanmak daha güvenlidir.
@Table(name = "users")

// --- LOMBOK ANNOTATIONLARI (Boilerplate koddan kurtulmak için) ---
@Getter
@Setter
@NoArgsConstructor // JPA, nesneleri yaratmak için boş bir constructor'a ihtiyaç duyar.
public class User {

    // 3. @Id: Bu alanın, tablonun "Primary Key" (Birincil Anahtar)
    //    olduğunu belirtir.
    @Id
    // 4. @GeneratedValue: Bu ID'nin veritabanı tarafından
    //    otomatik olarak (örn: auto-increment) üretilmesini sağlar
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 5. Kaydetmek istediğimiz asıl veri.
    //    (String username) yerine (String name) kullanalım,
    //    daha genel bir ifade olur.
    private String name;

    // 6. Bu, sadece veriyi kolayca yaratmak için eklediğimiz
    //    ek bir constructor (Lombok'un @NoArgsConstructor'ına ek olarak)
    public User(String name) {
        this.name = name;
    }
}
