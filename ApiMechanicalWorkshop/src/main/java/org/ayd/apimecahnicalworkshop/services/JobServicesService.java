package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.job.JobServicesDTO;
import org.ayd.apimecahnicalworkshop.entities.Job;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.ayd.apimecahnicalworkshop.repositories.JobRepository;
import org.ayd.apimecahnicalworkshop.repositories.JobServicesRepository;
import org.ayd.apimecahnicalworkshop.repositories.ServiceTypeRepository;
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
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public JobServicesDTO save(JobServicesDTO jobServicesDTO) {
        Job job = jobRepository.findByJobId(jobServicesDTO.getJobId());
        ServiceType serviceType = serviceTypeRepository.findByServiceTypeId(jobServicesDTO.getServiceTypeId());
        return map.mapToDTO(repository.save(map.mapToEntity(jobServicesDTO, job, serviceType)));
    }

    public List<JobServicesDTO> findJobServicesByJobId(Long jobId) {
        List<JobsServices> jobServices = repository.findJobServicesByJob_JobId(jobId);
        return jobServices.stream().map(map::mapToDTO).toList();
    }
}
