package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.job.JobAssigmentDTO;
import org.ayd.apimecahnicalworkshop.services.JobAssigmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobAssigments")
public class JobAssigmentController {

    @Autowired
    private JobAssigmentService service;

    public JobAssigmentDTO save(@RequestBody JobAssigmentDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/byJob/{jobId}")
    public List<JobAssigmentDTO> findByJob(@PathVariable Long jobId){
        return service.findByJob(jobId);
    }

}
