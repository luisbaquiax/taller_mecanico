package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.StatusJob;
import org.ayd.apimecahnicalworkshop.services.StatusJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statusJobs")
public class StatusJobController {
    @Autowired
    private StatusJobService statusJobService;

    @GetMapping("/all")
    public List<StatusJob> getAllStatusJobs() {
        return statusJobService.getAllStatusJobs();
    }
}
