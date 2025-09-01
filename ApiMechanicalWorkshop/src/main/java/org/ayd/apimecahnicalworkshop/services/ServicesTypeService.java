package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.job.ServicesTypeDTO;
import org.ayd.apimecahnicalworkshop.entities.ServicesTypes;
import org.ayd.apimecahnicalworkshop.repositories.ServiceTypeRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.ServicesTypeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesTypeService {
    @Autowired
    private ServiceTypeRepository repository;
    @Autowired
    private ServicesTypeConvert convert;

    public ServicesTypeDTO findById(Long id) {
        ServicesTypes aux = repository.findByServiceTypeId(id);
        if (aux == null) {
            throw new ErrorApi(404, "El tipo de servicio no existe.");
        }
        return convert.mapToDTO(aux);
    }

    public List<ServicesTypeDTO> findAll() {
        List<ServicesTypes> serviceTypes = (List<ServicesTypes>) repository.findAll();
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
        ServicesTypes aux = repository.findByServiceTypeId(servicesTypeDTO.getServiceTypeId());
        if (aux != null) {
            // Update service type
            aux.setNameServiceType(servicesTypeDTO.getNameServiceType());
            aux.setBasePrice(servicesTypeDTO.getBasePrice());
            aux.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
            return convert.mapToDTO(repository.save(aux));
        } else {
            // Create new service type
            ServicesTypes aux2 = repository.findByNameServiceTypeEqualsIgnoreCase(servicesTypeDTO.getNameServiceType());
            if (aux2 != null) {
                throw new ErrorApi(400, "El tipo de servicio ya existe.");
            }
            ServicesTypes newServiceType = new ServicesTypes();
            newServiceType.setServiceTypeId(null);
            newServiceType.setNameServiceType(servicesTypeDTO.getNameServiceType());
            newServiceType.setBasePrice(servicesTypeDTO.getBasePrice());
            newServiceType.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
            return convert.mapToDTO(repository.save(newServiceType));
        }
    }

}
