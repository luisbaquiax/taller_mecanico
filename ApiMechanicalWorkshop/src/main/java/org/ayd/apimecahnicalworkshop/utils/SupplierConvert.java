package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.suplierdto.SupplierDTO;
import org.ayd.apimecahnicalworkshop.entities.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierConvert {

    public SupplierDTO mapToDTO(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setUserId(supplier.getUserId());
        supplierDTO.setTypeSupplier(supplier.getTypeSupplier());
        return supplierDTO;
    }

    public Supplier mapToEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierDTO.getId());
        supplier.setUserId(supplierDTO.getUserId());
        supplier.setTypeSupplier(supplierDTO.getTypeSupplier());
        return supplier;
    }
}
