package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {

    List<Job> findJobByStatusJobId(long jobId);

    List<Job> findJobByStatusJobId(Long statusJobId);

    Job getByJobId(Long jobId);
}
