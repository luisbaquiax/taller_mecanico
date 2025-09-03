package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.StatusJob;
import org.ayd.apimecahnicalworkshop.repositories.StatusJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusJobService {
    @Autowired
    private StatusJobRepository statusJobRepository;

    public List<StatusJob> getAllStatusJobs() {
        return (List<StatusJob>) statusJobRepository.findAll();
    }

}
