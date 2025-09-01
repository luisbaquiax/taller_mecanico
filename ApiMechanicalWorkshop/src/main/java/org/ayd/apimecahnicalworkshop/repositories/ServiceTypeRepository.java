package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.ServicesTypes;
import org.springframework.data.repository.CrudRepository;

public interface ServiceTypeRepository extends CrudRepository<ServicesTypes, Long> {

    ServicesTypes findByServiceTypeId(Long id);

    ServicesTypes findByNameServiceTypeEqualsIgnoreCase(String name);

}
