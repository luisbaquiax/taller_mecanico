package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.Job;
import org.ayd.apimecahnicalworkshop.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/byId/{jobId}")
    public Job findByJobId(@PathVariable Long jobId){
        return jobService.findByJobId(jobId);
    }

    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping("/save")
    public Job saveJob(@RequestBody Job job, @RequestParam ArrayList<Long> servicesSelected) {
        return jobService.saveJob(job, servicesSelected);
    }

    @PutMapping("/update")
    public Job updateJob(@RequestBody Job job) {
        return jobService.updateJob(job);
    }

    @GetMapping("/jobsByStatusId/{statusJobId}")
    public List<Job> getJobsByStatusJobId(@PathVariable Long statusJobId) {
        return jobService.findJobByStatusJobId(statusJobId);
    }

}
