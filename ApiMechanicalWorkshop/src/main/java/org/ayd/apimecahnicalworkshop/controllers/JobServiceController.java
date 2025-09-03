package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.job.JobServicesDTO;
import org.ayd.apimecahnicalworkshop.services.JobServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobServices")
public class JobServiceController {
    @Autowired
    private JobServicesService service;

    @PostMapping("/save")
    public JobServicesDTO saveJobService(@RequestBody JobServicesDTO jobServicesDTO) {
        return service.save(jobServicesDTO);
    }

    @GetMapping("/jobServicesByJobId/{jobId}")
    public List<JobServicesDTO> getJobServicesByJobId(@PathVariable Long jobId){
        return service.findJobServicesByJobId(jobId);
    }
}
