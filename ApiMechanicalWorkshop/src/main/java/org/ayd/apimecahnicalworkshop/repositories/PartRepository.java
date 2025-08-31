package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Part;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartRepository extends CrudRepository<Part, Long> {
    List<Part> getPartByActive(Boolean active);

    Part findPartByNamePartIgnoreCaseAndSupplierId(String namePart, Long supplierId);
}
