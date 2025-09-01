package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.JobAssigment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobAssigmentRepository extends CrudRepository<JobAssigment, Long> {

    List<JobAssigment> findByJobId(Long jobId);
    JobAssigment findByJobAssignmentIdAndUserId(Long jobId, Long userId);
}
