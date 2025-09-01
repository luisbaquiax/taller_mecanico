package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.job.ServicesTypeDTO;
import org.ayd.apimecahnicalworkshop.services.ServicesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serviceTypes")
public class ServiceTypesController {

    @Autowired
    private ServicesTypeService serviceTypeService;

    @PostMapping("/save")
    public ServicesTypeDTO save(@RequestBody ServicesTypeDTO servicesTypeDTO) {
        return serviceTypeService.save(servicesTypeDTO);
    }

    @GetMapping("/all")
    public List<ServicesTypeDTO> findAll() {
        return serviceTypeService.findAll();
    }

    @GetMapping("/serviceTypeByJobId/{jobId}")
    public ServicesTypeDTO getServicesTypeByJobId(@PathVariable Long jobId){
        return serviceTypeService.findById(jobId);
    }

}
