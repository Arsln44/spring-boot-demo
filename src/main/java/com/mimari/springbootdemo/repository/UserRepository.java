package com.mimari.springbootdemo.repository;

import com.mimari.springbootdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 1. @Repository: Bu bir "Stereotip"tir (@Service gibi).
//    Spring'e bunun bir Veri Katmanı Bean'i olduğunu söyler
//    ve hata çevrimini (Exception Translation) açar.
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // 2. MUCİZE BURADA:
    //    Biz bu arayüzün İÇİNİ BOŞ BIRAKIYORUZ.

    // 3. JpaRepository<User, Long> ne demektir?
    //    "Ben, 'User' (Varlık) sınıfı için bir depo arayüzüyüm ve
    //    o 'User'ın ID'sinin tipi 'Long'dur."

    //    Sadece bu 'extends' (kalıtım) işlemi sayesinde, Spring Data JPA
    //    bizim için *otomatik olarak* şu metotları (ve daha fazlasını)
    //    arka planda IMPLEMENTE EDER (SQL'e çevirir):
    //
    //    - save(User user)           -> INSERT veya UPDATE
    //    - findById(Long id)         -> SELECT * FROM users WHERE id=?
    //    - findAll()                 -> SELECT * FROM users
    //    - delete(User user)         -> DELETE
}
