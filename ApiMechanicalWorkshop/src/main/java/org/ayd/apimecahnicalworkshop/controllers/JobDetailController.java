package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.DetailJobDTO;
import org.ayd.apimecahnicalworkshop.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobDetailController {

    @Autowired
    private JobService jobService;

    @GetMapping("/details/all")
    public ResponseEntity<List<DetailJobDTO>> getJobDetails() {
        List<DetailJobDTO> jobDetails = jobService.getAllJobDetails();
        return ResponseEntity.ok(jobDetails);
    }

    @GetMapping("/details/user/{userId}")
    public ResponseEntity<List<DetailJobDTO>> getJobDetailsByUserId(@PathVariable Integer userId) {
        List<DetailJobDTO> jobDetails = jobService.getJobDetailsByUserId(userId);
        return ResponseEntity.ok(jobDetails);
    }

    @GetMapping("/details/user-status")
    public ResponseEntity<List<DetailJobDTO>> getJobDetailsByUserAndStatus(
            @RequestParam Integer userId,
            @RequestParam Integer statusId) {
        List<DetailJobDTO> jobDetails = jobService.getJobDetailsByUserAndStatus(userId, statusId);
        return ResponseEntity.ok(jobDetails);
    }
}
