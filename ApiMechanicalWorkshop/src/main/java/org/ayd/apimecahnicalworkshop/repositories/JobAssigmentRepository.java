package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.JobAssigment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobAssigmentRepository extends CrudRepository<JobAssigment, Long> {
    List<JobAssigment> findByUserIdAndUnassignedAtIsNull(Long userId);
    List<JobAssigment> findByUserId(Long userId);
    List<JobAssigment> findByJobId(Long jobId);
    boolean existsByJobIdAndUserIdAndUnassignedAtIsNull(Long jobId, Long userId);
}
