package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.job.JobAssigmentDTO;
import org.ayd.apimecahnicalworkshop.entities.JobAssigment;
import org.ayd.apimecahnicalworkshop.repositories.JobAssigmentRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.JobAssigmentConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAssigmentService {

    @Autowired
    private JobAssigmentRepository repository;
    @Autowired
    private JobAssigmentConvert convert;

    public JobAssigmentDTO save(JobAssigmentDTO jobAssigmentDTO){
        JobAssigment aux = repository.findByJobAssignmentIdAndUserId(jobAssigmentDTO.getJobAssignmentId(), jobAssigmentDTO.getUserId());
        if(aux !=null){
            throw new ErrorApi(400, "El usuario ya esta asignado a este trabajo.");
        }
        JobAssigment jobAssigment = convert.mapToEntity(jobAssigmentDTO);
        jobAssigment.setJobAssignmentId(null);
        repository.save(jobAssigment);
        return convert.mapToDTO(jobAssigment);
    }

    public List<JobAssigment> findAll() {
        return (List<JobAssigment>) repository.findAll();
    }

    public List<JobAssigmentDTO> findByJob(Long jobId){
        return repository.findByJobId(jobId).stream().map(convert::mapToDTO).toList();
    }
}
