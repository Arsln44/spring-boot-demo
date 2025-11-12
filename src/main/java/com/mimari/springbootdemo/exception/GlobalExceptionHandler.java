package com.mimari.springbootdemo.exception;

import com.mimari.springbootdemo.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

// 1. @RestControllerAdvice: Bu anotasyon, bu sınıfı "Global bir Hata Yakalayıcı"
//    olarak işaretler. Projedeki TÜM @RestController'ları dinler.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 2. @ExceptionHandler: Bu metodu, "DataIntegrityViolationException"
    //    hatası fırlatıldığında tetikle.
    @ExceptionHandler(DataIntegrityViolationException.class)
    // 3. @ResponseStatus: Bu hata yakalandığında,
    //    HTTP durum kodunu '500' (Sunucu Hatası) DEĞİL,
    //    '409 CONFLICT' (Çakışma) olarak ayarla. Bu, mimari açıdan
    //    "bu email zaten var" için DOĞRU durum kodudur.
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {

        // 4. TEMİZ MESAJI OLUŞTURMA:
        //    Korkunç Stack Trace'i sızdırmak YERİNE,
        //    güvenli ve temiz DTO'muzu oluşturuyoruz.
        String errorMessage = "Veri bütünlüğü ihlali: Bu e-posta adresi zaten kullanılıyor";

        // 5. TEMİZ CEVABI DÖNDÜRME:
        //    @RestControllerAdvice, bu nesneyi otomatik olarak JSON'a çevirir.
        return new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                errorMessage
        );
    }

    // Not: Buraya 'UserNotFoundException' gibi diğer özel
    // Exception'lar için yeni @ExceptionHandler metotları ekleyebiliriz.
}
