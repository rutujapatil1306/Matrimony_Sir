package com.spring.jwt.HoroscopeDetails;

import com.spring.jwt.entity.HoroscopeDetails;
import com.spring.jwt.entity.User;

public class HoroscopeDetailsMapper {

    public static HoroscopeDetailsDTO toDTO(HoroscopeDetails entity) {
        return new HoroscopeDetailsDTO(
                entity.getHoroscopeDetailsId(),
                entity.getDob(),
                entity.getTime(),
                entity.getBirthPlace(),
                entity.getRashi(),
                entity.getNakshatra(),
                entity.getCharan(),
                entity.getNadi(),
                entity.getGan(),
                entity.getMangal(),
                entity.getGotra(),
                entity.getDevak(),
                entity.getUser().getId()
        );
    }

    public static HoroscopeDetails toEntity(HoroscopeDetailsDTO dto, User user) {
        HoroscopeDetails h = new HoroscopeDetails();
        h.setHoroscopeDetailsId(dto.getHoroscopeDetailsId());
        h.setDob(dto.getDob());
        h.setTime(dto.getTime());
        h.setBirthPlace(dto.getBirthPlace());
        h.setRashi(dto.getRashi());
        h.setNakshatra(dto.getNakshatra());
        h.setCharan(dto.getCharan());
        h.setNadi(dto.getNadi());
        h.setGan(dto.getGan());
        h.setMangal(dto.getMangal());
        h.setGotra(dto.getGotra());
        h.setDevak(dto.getDevak());
        h.setUser(user);
        return h;
    }
}
