package com.oldtownbarber.salon_service.service.implementation;

import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import com.oldtownbarber.salon_service.payload.UserDTO;
import com.oldtownbarber.salon_service.repository.SalonRepository;
import com.oldtownbarber.salon_service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImplementation implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO salonDTO, UserDTO userDTO) {

        Salon createdSalon = new Salon();
        createdSalon.setName(salonDTO.getName());
        createdSalon.setAddress(salonDTO.getAddress());
        createdSalon.setEmail(salonDTO.getEmail());
        createdSalon.setCity(salonDTO.getCity());
        createdSalon.setImages(salonDTO.getImages());
        createdSalon.setOwnerId(userDTO.getId());
        createdSalon.setOpenTime(salonDTO.getOpenTime());
        createdSalon.setCloseTime(salonDTO.getCloseTime());
        createdSalon.setPhoneNumber(salonDTO.getPhoneNumber());

        return salonRepository.save(createdSalon);
    }

    @Override
    public Salon updateSalon(SalonDTO salonDTO, UserDTO userDTO, Long salonId) throws Exception {

        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon != null && existingSalon.getOwnerId().equals(userDTO.getId())) {
            existingSalon.setName(salonDTO.getName());
            existingSalon.setCity(salonDTO.getCity());
            existingSalon.setAddress(salonDTO.getAddress());
            existingSalon.setEmail(salonDTO.getEmail());
            existingSalon.setOpenTime(salonDTO.getOpenTime());
            existingSalon.setCloseTime(salonDTO.getCloseTime());
            existingSalon.setPhoneNumber(salonDTO.getPhoneNumber());
            existingSalon.setImages(salonDTO.getImages());
            existingSalon.setOwnerId(userDTO.getId());

            return salonRepository.save(existingSalon);
        }

        throw new Exception("Salon not exist");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon == null) {
            throw new Exception("Salon not exist");
        }

        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findSalonByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
