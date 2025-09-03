package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.JobsServices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobServicesRepository extends CrudRepository<JobsServices, Long> {

    List<JobsServices> findJobServicesByJob_JobId(Long jobJobId);
}
