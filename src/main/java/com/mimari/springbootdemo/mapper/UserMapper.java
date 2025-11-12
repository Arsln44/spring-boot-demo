package com.mimari.springbootdemo.mapper;

import com.mimari.springbootdemo.domain.User;
import com.mimari.springbootdemo.dto.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// 1. @Mapper: MapStruct'a "Bu arayüz bir dönüştürücüdür" der.
// 2. componentModel = "spring": MapStruct'a "Benim için üreteceğin
//    gerçek sınıfı (UserMapperImpl) bir Spring Bean'i (@Component)
//    olarak işaretle" der. Bu, onu @Autowired ile enjekte
//    edebilmemizi sağlar.
@Mapper(componentModel = "spring")
public interface UserMapper {

    // 3. Bu, bizim "sözleşmemizdir".
    //    "Bana bir DTO ver, sana bir Entity döneyim"
    //    MapStruct, alan isimleri AYNIYSA (DTO'da 'name', Entity'de 'name')
    //    bunları OTOMATİK olarak eşler (user.setName(dto.getName())).

    // 4. @Mapping (Özelleştirme):
    //    'password' alanı için otomatik eşleşme İSTEMİYORUZ.
    //    Parolanın 'null' olarak (veya görmezden gelinerek)
    //    aktarılmasını istiyoruz, çünkü parolayı UserService'de
    //    manuel olarak HASH'leyeceğiz.
    @Mapping(target = "password", ignore = true)
    User dtoToEntity(RegisterRequestDTO dto);

    // Not: Buraya 'entityToDto' gibi tersi metotları da
    // ekleyebilirdik.
}
