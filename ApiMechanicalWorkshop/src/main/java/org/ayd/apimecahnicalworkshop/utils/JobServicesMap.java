package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.job.JobServicesDTO;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.springframework.stereotype.Service;

@Service
public class JobServicesMap {
    public JobServicesDTO mapToDTO(JobsServices jobsServices) {
        JobServicesDTO jobServicesDTO = new JobServicesDTO();
        jobServicesDTO.setJobServiceId(jobsServices.getJobServiceId());
        jobServicesDTO.setJobId(jobsServices.getJobId());
        jobServicesDTO.setServiceTypeId(jobsServices.getServiceTypeId());
        jobServicesDTO.setQuantity(jobsServices.getQuantity());
        jobServicesDTO.setPrice(jobsServices.getPrice());
        return jobServicesDTO;
    }

    public JobsServices mapToEntity(JobServicesDTO jobServicesDTO) {
        JobsServices jobsServices = new JobsServices();
        jobsServices.setJobServiceId(jobServicesDTO.getJobServiceId());
        jobsServices.setJobId(jobServicesDTO.getJobId());
        jobsServices.setServiceTypeId(jobServicesDTO.getServiceTypeId());
        jobsServices.setQuantity(jobServicesDTO.getQuantity());
        jobsServices.setPrice(jobServicesDTO.getPrice());
        return jobsServices;
    }
}
