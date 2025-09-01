package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.job.ServicesTypeDTO;
import org.ayd.apimecahnicalworkshop.entities.ServicesTypes;
import org.springframework.stereotype.Service;

@Service
public class ServicesTypeConvert {

    public ServicesTypeDTO mapToDTO(ServicesTypes servicesTypes) {
        ServicesTypeDTO servicesTypeDTO = new ServicesTypeDTO();
        servicesTypeDTO.setServiceTypeId(servicesTypes.getServiceTypeId());
        servicesTypeDTO.setNameServiceType(servicesTypes.getNameServiceType());
        servicesTypeDTO.setDescriptionServiceType(servicesTypes.getDescriptionServiceType());
        servicesTypeDTO.setBasePrice(servicesTypes.getBasePrice());
        return servicesTypeDTO;
    }

    public ServicesTypes maToEntity(ServicesTypeDTO servicesTypeDTO) {
        ServicesTypes servicesTypes = new ServicesTypes();
        servicesTypes.setServiceTypeId(servicesTypeDTO.getServiceTypeId());
        servicesTypes.setNameServiceType(servicesTypeDTO.getNameServiceType());
        servicesTypes.setDescriptionServiceType(servicesTypeDTO.getDescriptionServiceType());
        servicesTypes.setBasePrice(servicesTypeDTO.getBasePrice());
        return servicesTypes;
    }
}
