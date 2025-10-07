package com.oldtownbarber.service_offering.controller;

import com.oldtownbarber.service_offering.dto.CategoryDTO;
import com.oldtownbarber.service_offering.dto.SalonDTO;
import com.oldtownbarber.service_offering.dto.ServiceDTO;
import com.oldtownbarber.service_offering.model.ServiceOffering;
import com.oldtownbarber.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(serviceOfferings);

    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long serviceId, @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOfferings = serviceOfferingService.updateService(serviceId, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);

    }


}
