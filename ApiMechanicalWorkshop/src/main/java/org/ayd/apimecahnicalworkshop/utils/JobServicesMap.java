package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.job.JobServicesDTO;
import org.ayd.apimecahnicalworkshop.entities.Job;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.ayd.apimecahnicalworkshop.services.JobService;
import org.ayd.apimecahnicalworkshop.services.ServicesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServicesMap {


    public JobServicesDTO mapToDTO(JobsServices jobsServices) {
        JobServicesDTO jobServicesDTO = new JobServicesDTO();
        jobServicesDTO.setJobServiceId(jobsServices.getJobServiceId());
        jobServicesDTO.setJobId(jobsServices.getJob().getJobId());
        jobServicesDTO.setServiceTypeId(jobsServices.getServiceType().getServiceTypeId());
        jobServicesDTO.setQuantity(jobsServices.getQuantity());
        jobServicesDTO.setPrice(jobsServices.getPrice());
        return jobServicesDTO;
    }

    public JobsServices mapToEntity(JobServicesDTO jobServicesDTO, Job job, ServiceType serviceType) {
        JobsServices jobsServices = new JobsServices();
        jobsServices.setJobServiceId(jobServicesDTO.getJobServiceId());
        jobsServices.setJob(job);
        jobsServices.setServiceType(serviceType);
        jobsServices.setQuantity(jobServicesDTO.getQuantity());
        jobsServices.setPrice(jobServicesDTO.getPrice());
        return jobsServices;
    }
}
