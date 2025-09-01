package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.typesServices.ServiceTypeDTO;
import org.ayd.apimecahnicalworkshop.services.ServicesTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serviceType")
@CrossOrigin(origins = "*")
public class ServiceTypeController {

    private final ServicesTypeService service;

    public ServiceTypeController(ServicesTypeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<ServiceTypeDTO> getAll() {
        return service.getAll();
    }

    @PostMapping ("/create")
    public ServiceTypeDTO create(@RequestBody ServiceTypeDTO dto) {
        return service.create(dto);
    }

}
