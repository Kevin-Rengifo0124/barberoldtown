package com.oldtownbarber.service_offering.service.implementation;

import com.oldtownbarber.service_offering.dto.CategoryDTO;
import com.oldtownbarber.service_offering.dto.SalonDTO;
import com.oldtownbarber.service_offering.dto.ServiceDTO;
import com.oldtownbarber.service_offering.model.ServiceOffering;
import com.oldtownbarber.service_offering.repository.ServiceOfferingRepository;
import com.oldtownbarber.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImplementation implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception {
        ServiceOffering existingService = serviceOfferingRepository.findById(serviceId)
                .orElseThrow(() -> new Exception("No existe ese servicio con ese ID " + serviceId));

        // Solo actualizar si el valor no es null o diferente de 0
        if (serviceOffering.getName() != null && !serviceOffering.getName().isEmpty()) {
            existingService.setName(serviceOffering.getName());
        }

        if (serviceOffering.getDescription() != null && !serviceOffering.getDescription().isEmpty()) {
            existingService.setDescription(serviceOffering.getDescription());
        }

        if (serviceOffering.getPrice() > 0) {
            existingService.setPrice(serviceOffering.getPrice());
        }

        if (serviceOffering.getDuration() > 0) {
            existingService.setDuration(serviceOffering.getDuration());
        }

        if (serviceOffering.getImage() != null && !serviceOffering.getImage().isEmpty()) {
            existingService.setImage(serviceOffering.getImage());
        }

        if (serviceOffering.getCategoryId() != null) {
            existingService.setCategoryId(serviceOffering.getCategoryId());
        }

        return serviceOfferingRepository.save(existingService);
    }


    @Override
    public Set<ServiceOffering> getAllServiceBySalon(Long salonId, Long categoryId) {
        //Buscar todos los servicios que pertenecen al salón con el ID dado
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);

        //Si el usuario pasó un categoryId (no es nulo), se filtran los servicios
        if (categoryId != null) {
            services = services.stream()
                    //Filtrar los servicios:
                    // - Solo los que tengan un categoryId no nulo
                    // - Y cuyo categoryId sea igual al que vino por parámetro
                    .filter(service -> service.getCategoryId() != null &&
                            Objects.equals(service.getCategoryId(), categoryId))
                    //Convertir el resultado filtrado de nuevo a un Set
                    .collect(Collectors.toSet());
        }

        //Retornar el conjunto final (filtrado o no, dependiendo del categoryId)
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        //Buscar todos los servicios cuyos IDs estén dentro del conjunto "ids"
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);

        //Convertir la lista obtenida a un conjunto (HashSet) para eliminar duplicados
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering service = serviceOfferingRepository.findById(id).orElse(null);

        if (service == null) {
            throw new Exception("No existe ese servicio con ese ID " + id);
        }

        return service;
    }
}
