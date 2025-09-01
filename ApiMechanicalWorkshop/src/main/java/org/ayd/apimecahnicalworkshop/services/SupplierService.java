package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.suplierdto.SupplierDTO;
import org.ayd.apimecahnicalworkshop.entities.Supplier;
import org.ayd.apimecahnicalworkshop.repositories.SupplierRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.SupplierConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository repository;
    @Autowired
    private SupplierConvert convert;

    public List<Supplier> findAll() {
        return (List<Supplier>) repository.findAll();
    }

    public SupplierDTO findById(Long id) {
        return convert.mapToDTO(repository.findById(id).orElseThrow(() -> new ErrorApi(404, "El proveedor no existe")));
    }

    public SupplierDTO findByUserId(Long userId) {
        return convert.mapToDTO(repository.findByUserId(userId));
    }
}
