package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.TypeJob;
import org.ayd.apimecahnicalworkshop.services.TypeJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/typeJobs")
public class TypeJobController {
    @Autowired
    private TypeJobsService service;

    @GetMapping("/all")
    public List<TypeJob> getAllTypeJobs() {
        return service.getAllTypeJobs();
    }
}
