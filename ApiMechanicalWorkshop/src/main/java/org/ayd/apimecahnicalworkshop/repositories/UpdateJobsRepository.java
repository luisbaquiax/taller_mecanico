package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.UpdateJob;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UpdateJobsRepository extends CrudRepository<UpdateJob, Long> {

    List<UpdateJob> findByJobIdOrderByCreatedAtDesc(Long jobId);
    List<UpdateJob> findByJobIdAndCreatedBy(Long jobId, Long createdBy);
    List<UpdateJob> findByCreatedBy(Long createdBy);

}
