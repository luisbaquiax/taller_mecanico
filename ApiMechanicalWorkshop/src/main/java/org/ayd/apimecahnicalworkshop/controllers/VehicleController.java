package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.vehicledto.VehicleDTO;
import org.ayd.apimecahnicalworkshop.entities.Vehicle;
import org.ayd.apimecahnicalworkshop.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @GetMapping("/all")
    public List<VehicleDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/withoutClient")
    public List<VehicleDTO> getVehiclesWithoutClient() {
        return service.getVehiclesByClientId();
    }

    @PostMapping("/saveVehicle")
    public VehicleDTO createVehicle(VehicleDTO vehicle) {
        return service.save(vehicle);
    }
}
