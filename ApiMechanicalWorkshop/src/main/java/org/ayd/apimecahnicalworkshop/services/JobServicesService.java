package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.job.JobServicesDTO;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.ayd.apimecahnicalworkshop.repositories.JobServicesRepository;
import org.ayd.apimecahnicalworkshop.utils.JobServicesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServicesService {
    @Autowired
    private JobServicesRepository repository;
    @Autowired
    private JobServicesMap map;

    public JobServicesDTO save(JobServicesDTO jobServicesDTO) {
        return map.mapToDTO(repository.save(map.mapToEntity(jobServicesDTO)));
    }

    public List<JobServicesDTO> findJobServicesByJobId(Long jobId) {
        List<JobsServices> jobServices = repository.findJobServicesByJobId(jobId);
        return jobServices.stream().map(map::mapToDTO).toList();
    }
}
