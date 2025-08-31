package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.suplierdto.SupplierDTO;
import org.ayd.apimecahnicalworkshop.services.SupplierService;
import org.ayd.apimecahnicalworkshop.utils.SupplierConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @Autowired
    private SupplierConvert convert;

    @GetMapping("/all")
    public List<SupplierDTO> getAll() {
        return service.findAll().stream().map(convert::mapToDTO).toList();
    }

    @GetMapping("/userId/{id}")
    public SupplierDTO findByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }
}
