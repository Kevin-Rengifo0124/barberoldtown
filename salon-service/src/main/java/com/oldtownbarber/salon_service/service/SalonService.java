package com.oldtownbarber.salon_service.service;

import com.oldtownbarber.salon_service.exception.SalonException;
import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import com.oldtownbarber.salon_service.payload.UserDTO;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDTO salonDTO, UserDTO userDTO);

    Salon updateSalon(SalonDTO salonDTO, UserDTO userDTO, Long salonId) throws SalonException;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws SalonException;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}