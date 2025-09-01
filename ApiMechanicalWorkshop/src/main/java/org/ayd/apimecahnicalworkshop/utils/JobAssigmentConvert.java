package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.job.JobAssigmentDTO;
import org.ayd.apimecahnicalworkshop.entities.JobAssigment;
import org.springframework.stereotype.Service;

@Service
public class JobAssigmentConvert {

    public JobAssigmentDTO mapToDTO(JobAssigment jobAssigment) {
        JobAssigmentDTO jobAssigmentDTO = new JobAssigmentDTO();
        jobAssigmentDTO.setJobAssignmentId(jobAssigment.getJobAssignmentId());
        jobAssigmentDTO.setJobId(jobAssigment.getJobId());
        jobAssigmentDTO.setUserId(jobAssigment.getUserId());
        jobAssigmentDTO.setRoleAssignment(jobAssigment.getRoleAssignment());
        jobAssigmentDTO.setNotes(jobAssigment.getNotes());
        return jobAssigmentDTO;
    }

    public JobAssigment mapToEntity(JobAssigmentDTO jobAssigmentDTO) {
        JobAssigment jobAssigment = new JobAssigment();
        jobAssigment.setJobAssignmentId(jobAssigmentDTO.getJobAssignmentId());
        jobAssigment.setJobId(jobAssigmentDTO.getJobId());
        jobAssigment.setUserId(jobAssigmentDTO.getUserId());
        jobAssigment.setRoleAssignment(jobAssigmentDTO.getRoleAssignment());
        jobAssigment.setNotes(jobAssigmentDTO.getNotes());
        return jobAssigment;
    }
}
