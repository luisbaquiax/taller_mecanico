package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.springframework.data.repository.CrudRepository;

public interface ServiceTypeRepository extends CrudRepository<ServiceType, Long> {

    ServiceType findByServiceTypeId(Long id);

    ServiceType findByNameServiceTypeEqualsIgnoreCase(String name);

}
