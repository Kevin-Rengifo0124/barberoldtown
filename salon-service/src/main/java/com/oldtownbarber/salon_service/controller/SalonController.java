package com.oldtownbarber.salon_service.controller;

import com.oldtownbarber.salon_service.mapper.SalonMapper;
import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import com.oldtownbarber.salon_service.payload.UserDTO;
import com.oldtownbarber.salon_service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO requestDTO) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.createSalon(requestDTO, userDTO);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDTO> updateSalon(@PathVariable("id") Long salonId, @RequestBody SalonDTO requestDTO) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.updateSalon(requestDTO, userDTO, salonId);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() {
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream()
                .map(SalonMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.getSalonById(salonId);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) {
        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS = salons.stream()
                .map(SalonMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long salonId) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);

    }
}