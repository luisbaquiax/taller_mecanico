package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.JobParts;
import org.ayd.apimecahnicalworkshop.repositories.JobPartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPartsService {
    @Autowired
    private JobPartsRepository repository;

    public List<JobParts> findAll() {
        return (List<JobParts>) repository.findAll();
    }

    public List<JobParts> findJobPartsByJobId(Long jobId) {
        return repository.findJobPartsByJobId(jobId);
    }

}
