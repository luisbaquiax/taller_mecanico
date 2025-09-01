package org.ayd.apimecahnicalworkshop.services;

import org.springframework.stereotype.Service;
import org.ayd.apimecahnicalworkshop.dto.typesServices.ServiceTypeDTO;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.ayd.apimecahnicalworkshop.repositories.ServiceTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServicesTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public List<ServiceTypeDTO> getAll() {
        List<ServiceType> serviceTypes = (List<ServiceType>) serviceTypeRepository.findAll();

        return serviceTypes.stream()
                .map(s -> new ServiceTypeDTO(
                        s.getServiceTypeId(),
                        s.getNameServiceType(),
                        s.getDescriptionServiceType(),
                        s.getBasePrice()
                ))
                .collect(Collectors.toList());
    }

    public ServiceTypeDTO create(ServiceTypeDTO dto) {
        ServiceType entity = new ServiceType(
                null,
                dto.getName(),
                dto.getDescription(),
                dto.getPrice()
        );
        ServiceType saved = serviceTypeRepository.save(entity);
        return new ServiceTypeDTO(
                saved.getServiceTypeId(),
                saved.getNameServiceType(),
                saved.getDescriptionServiceType(),
                saved.getBasePrice()
        );
    }

}
