package com.oldtownbarber.salon_service.service.implementation;

import com.oldtownbarber.salon_service.exception.SalonException;
import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import com.oldtownbarber.salon_service.payload.UserDTO;
import com.oldtownbarber.salon_service.repository.SalonRepository;
import com.oldtownbarber.salon_service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Salon updateSalon(SalonDTO salonDTO, UserDTO userDTO, Long salonId) throws SalonException {
        Optional<Salon> optionalSalon = salonRepository.findById(salonId);

        if (optionalSalon.isEmpty()) {
            throw new SalonException("Salón no encontrado con id " + salonId);
        }

        Salon existingSalon = optionalSalon.get();

        if (!existingSalon.getOwnerId().equals(userDTO.getId())) {
            throw new SalonException("No tienes permisos para actualizar este salón");
        }

        existingSalon.setName(salonDTO.getName());
        existingSalon.setCity(salonDTO.getCity());
        existingSalon.setAddress(salonDTO.getAddress());
        existingSalon.setEmail(salonDTO.getEmail());
        existingSalon.setOpenTime(salonDTO.getOpenTime());
        existingSalon.setCloseTime(salonDTO.getCloseTime());
        existingSalon.setPhoneNumber(salonDTO.getPhoneNumber());
        existingSalon.setImages(salonDTO.getImages());

        return salonRepository.save(existingSalon);
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws SalonException {
        return salonRepository.findById(salonId)
                .orElseThrow(() -> new SalonException("Salón no encontrado con id " + salonId));
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