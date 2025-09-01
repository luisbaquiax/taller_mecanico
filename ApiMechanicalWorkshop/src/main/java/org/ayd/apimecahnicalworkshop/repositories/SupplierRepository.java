package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Supplier findByUserId(Long userId);
}
