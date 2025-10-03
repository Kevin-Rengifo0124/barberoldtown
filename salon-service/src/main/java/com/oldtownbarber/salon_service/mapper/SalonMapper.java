package com.oldtownbarber.salon_service.mapper;

import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import org.springframework.stereotype.Component;

@Component
public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());

        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setImages(salon.getImages());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setEmail(salon.getEmail());
        return salonDTO;
    }
}