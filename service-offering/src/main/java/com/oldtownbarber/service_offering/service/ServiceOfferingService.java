package com.oldtownbarber.service_offering.service;

import com.oldtownbarber.service_offering.dto.CategoryDTO;
import com.oldtownbarber.service_offering.dto.SalonDTO;
import com.oldtownbarber.service_offering.dto.ServiceDTO;
import com.oldtownbarber.service_offering.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;

    Set<ServiceOffering> getAllServiceBySalon(Long salonId, Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
