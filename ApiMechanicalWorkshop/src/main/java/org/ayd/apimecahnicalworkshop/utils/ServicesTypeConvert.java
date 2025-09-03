package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.job.ServicesTypeDTO;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.springframework.stereotype.Service;

@Service
public class ServicesTypeConvert {

    public ServicesTypeDTO mapToDTO(ServiceType servicesTypes) {
        ServicesTypeDTO servicesTypeDTO = new ServicesTypeDTO();
        servicesTypeDTO.setServiceTypeId(servicesTypes.getServiceTypeId());
        servicesTypeDTO.setNameServiceType(servicesTypes.getNameServiceType());
        servicesTypeDTO.setDescriptionServiceType(servicesTypes.getDescriptionServiceType());
        servicesTypeDTO.setBasePrice(servicesTypes.getBasePrice());
        return servicesTypeDTO;
    }

    public ServiceType maToEntity(ServicesTypeDTO servicesTypeDTO) {
        ServiceType servicesTypes = new ServiceType();
        servicesTypes.setServiceTypeId(servicesTypeDTO.getServiceTypeId());
        servicesTypes.setNameServiceType(servicesTypeDTO.getNameServiceType());
        servicesTypes.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
        servicesTypes.setBasePrice(servicesTypeDTO.getBasePrice());
        return servicesTypes;
    }
}
