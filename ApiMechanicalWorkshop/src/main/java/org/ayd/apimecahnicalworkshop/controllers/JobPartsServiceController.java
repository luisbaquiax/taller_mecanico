package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.JobParts;
import org.ayd.apimecahnicalworkshop.services.JobPartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobParts")
public class JobPartsServiceController {
    @Autowired
    private JobPartsService jobPartsService;

    @GetMapping("/jobPartsByJobId/{jobId}")
    public List<JobParts> getJobPartsByJobId(@PathVariable Long jobId){
        return jobPartsService.findJobPartsByJobId(jobId);
    }
}
