package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.Job;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.ayd.apimecahnicalworkshop.entities.ServicesTypes;
import org.ayd.apimecahnicalworkshop.repositories.JobRepository;
import org.ayd.apimecahnicalworkshop.utils.JobServicesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository repository;
    @Autowired
    private JobServicesService serviceJobServices;
    @Autowired
    private JobServicesMap mapJobServices;
    @Autowired
    private ServicesTypeService servicesTypes;

    public Job findByJobId(Long jobId){
        return repository.getByJobId(jobId);
    }

    public List<Job> getAllJobs() {
        return (List<Job>) repository.findAll();
    }

    public Job saveJob(Job job, ArrayList<Long> servicesSelected) {
        // 1 is pending
        job.setJobId(null);
        job.setStatusJobId(1L);
        job.setStartedAt(null);
        job.setFinishedAt(null);

        Job jobSave = new Job();
        jobSave.setJobId(job.getJobId());
        jobSave.setCreatedBy(job.getCreatedBy());
        jobSave.setVehicleId(job.getVehicleId());
        jobSave.setStartedAt(job.getStartedAt());
        jobSave.setFinishedAt(job.getFinishedAt());
        jobSave.setTypeJobId(job.getTypeJobId());
        jobSave.setStatusJobId(job.getStatusJobId());
        jobSave.setDescription(job.getDescription());
        jobSave.setEstimatedHours(job.getEstimatedHours());

        repository.save(jobSave);

        for (Long serviceSelected : servicesSelected) {
            JobsServices jobService = new JobsServices();
            jobService.setJobId(jobSave.getJobId());
            jobService.setServiceTypeId(serviceSelected);
            jobService.setQuantity(1);
            jobService.setPrice(servicesTypes.findById(serviceSelected).getBasePrice());
            serviceJobServices.save(mapJobServices.mapToDTO(jobService));
        }
        return jobSave;
    }

    public Job updateJob(Job job) {
        Job aux = repository.findById(job.getJobId()).get();
        aux.setStatusJobId(job.getStatusJobId());
        aux.setDescription(job.getDescription());
        aux.setEstimatedHours(job.getEstimatedHours());
        return repository.save(aux);
    }

    public List<Job> findJobByStatusJobId(Long jobId) {
        return repository.findJobByStatusJobId(jobId);
    }

}
