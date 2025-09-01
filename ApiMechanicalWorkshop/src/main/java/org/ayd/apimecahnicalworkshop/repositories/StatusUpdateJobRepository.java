package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.StatusUpdateJob;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface StatusUpdateJobRepository extends CrudRepository<StatusUpdateJob, Long> {

    Optional<StatusUpdateJob> findByNameStatusUpdateJob(String nameStatusUpdateJob);

}
