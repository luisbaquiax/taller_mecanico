package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.job.ServicesTypeDTO;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.ServicesTypeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ayd.apimecahnicalworkshop.dto.typesServices.ServiceTypeDTO;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.ayd.apimecahnicalworkshop.repositories.ServiceTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesTypeService {

    @Autowired
    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ServicesTypeConvert convert;

    public ServiceType findServiceTypeById(Long id) {
        return serviceTypeRepository.findByServiceTypeId(id);
    }

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


    public ServicesTypeDTO findById(Long id) {
        ServiceType aux = serviceTypeRepository.findByServiceTypeId(id);
        if (aux == null) {
            throw new ErrorApi(404, "El tipo de servicio no existe.");
        }
        return convert.mapToDTO(aux);
    }

    public List<ServicesTypeDTO> findAll() {
        List<ServiceType> serviceTypes = (List<ServiceType>) serviceTypeRepository.findAll();
        return serviceTypes.stream().map(convert::mapToDTO).toList();
    }

    /**
     * Save or update a service type
     *
     * @param servicesTypeDTO - Service type to save or update
     * @return - Service type saved
     */
    public ServicesTypeDTO save(ServicesTypeDTO servicesTypeDTO) {
        if (servicesTypeDTO.getServiceTypeId() == 0) {
            servicesTypeDTO.setServiceTypeId(null);
        }
        ServiceType aux = serviceTypeRepository.findByServiceTypeId(servicesTypeDTO.getServiceTypeId());
        if (aux != null) {
            // Update service type
            aux.setNameServiceType(servicesTypeDTO.getNameServiceType());
            aux.setBasePrice(servicesTypeDTO.getBasePrice());
            aux.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
            return convert.mapToDTO(serviceTypeRepository.save(aux));
        } else {
            // Create new service type
            ServiceType aux2 = serviceTypeRepository.findByNameServiceTypeEqualsIgnoreCase(servicesTypeDTO.getNameServiceType());
            if (aux2 != null) {
                throw new ErrorApi(400, "El tipo de servicio ya existe.");
            }
            ServiceType newServiceType = new ServiceType();
            newServiceType.setServiceTypeId(null);
            newServiceType.setNameServiceType(servicesTypeDTO.getNameServiceType());
            newServiceType.setBasePrice(servicesTypeDTO.getBasePrice());
            newServiceType.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
            return convert.mapToDTO(serviceTypeRepository.save(newServiceType));
        }
    }

}
