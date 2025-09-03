package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.DetailJobDTO;
import org.ayd.apimecahnicalworkshop.entities.Job;
import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.ayd.apimecahnicalworkshop.entities.ServiceType;
import org.ayd.apimecahnicalworkshop.repositories.JobRepository;
import org.ayd.apimecahnicalworkshop.repositories.ServiceTypeRepository;
import org.ayd.apimecahnicalworkshop.utils.JobServicesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobServicesService serviceJobServices;
    @Autowired
    private JobServicesMap mapJobServices;
    @Autowired
    private ServicesTypeService servicesTypes;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public Job findByJobId(Long jobId){
        return jobRepository.getByJobId(jobId);
    }

    public List<Job> getAllJobs() {
        return (List<Job>) jobRepository.findAll();
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

        jobRepository.save(jobSave);

        for (Long serviceSelected : servicesSelected) {

            ServiceType serviceType = serviceTypeRepository.findById(serviceSelected).get();

            JobsServices jobService = new JobsServices();
            jobService.setJob(jobSave);
            jobService.setServiceType(serviceType);
            jobService.setQuantity(1);
            jobService.setPrice(serviceType.getBasePrice());
            serviceJobServices.save(mapJobServices.mapToDTO(jobService));
        }
        return jobSave;
    }

    public Job updateJob(Job job) {
        Job aux = jobRepository.findById(job.getJobId()).get();
        aux.setStatusJobId(job.getStatusJobId());
        aux.setDescription(job.getDescription());
        aux.setEstimatedHours(job.getEstimatedHours());
        return jobRepository.save(aux);
    }

    public List<Job> findJobByStatusJobId(Long jobId) {
        return jobRepository.findJobByStatusJobId(jobId);
    }

    public List<DetailJobDTO> getAllJobDetails() {
        List<Object[]> results = jobRepository.findAllJobDetails();
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

    public List<DetailJobDTO> getJobDetailsByUserId(Integer userId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserId(userId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }
    public List<DetailJobDTO> getJobDetailsByUserIdNamed(Integer userId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserIdNamed(userId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

    public List<DetailJobDTO> getJobDetailsByUserAndStatus(Integer userId, Integer statusId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserAndStatus(userId, statusId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

}
