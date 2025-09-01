package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.JobParts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobPartsRepository extends CrudRepository<JobParts, Long> {

    List<JobParts> findJobPartsByJobId(Long jobId);
}
