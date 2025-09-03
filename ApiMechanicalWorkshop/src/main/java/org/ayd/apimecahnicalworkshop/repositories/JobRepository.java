package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.dto.DetailJobDTO;
import org.ayd.apimecahnicalworkshop.entities.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findJobByStatusJobId(long jobId);

    Job findByJobId(Long jobId);

    List<Job> findJobByStatusJobId(Long statusJobId);

    Job getByJobId(Long jobId);

    @Query(value = "SELECT tj.name_type_job, " +
            "services_types.name_service_type, " +
            "status_jobs.name_status_job, " +
            "j.job_id, " +
            "v.model, " +
            "v.color, " +
            "u.name, " +
            "u.last_name, " +
            "j.created_at " +
            "FROM jobs j " +
            "INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id " +
            "INNER JOIN clients c ON v.client_id = c.client_id " +
            "INNER JOIN users u ON c.user_id = u.user_id " +
            "INNER JOIN status_jobs ON j.status_job_id = status_jobs.status_job_id " +
            "INNER JOIN type_jobs tj ON j.type_job_id = tj.type_job_id " +
            "INNER JOIN jobs_services js ON j.job_id = js.job_id " +
            "INNER JOIN services_types ON js.service_type_id = services_types.service_type_id",
            nativeQuery = true)
    List<Object[]> findAllJobDetails();

    @Query(value = "SELECT tj.name_type_job, " +
            "services_types.name_service_type, " +
            "status_jobs.name_status_job, " +
            "j.job_id, " +
            "v.model, " +
            "v.color, " +
            "u.name, " +
            "u.last_name, " +
            "j.created_at " +
            "FROM jobs j " +
            "INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id " +
            "INNER JOIN clients c ON v.client_id = c.client_id " +
            "INNER JOIN users u ON c.user_id = u.user_id " +
            "INNER JOIN status_jobs ON j.status_job_id = status_jobs.status_job_id " +
            "INNER JOIN type_jobs tj ON j.type_job_id = tj.type_job_id " +
            "INNER JOIN jobs_services js ON j.job_id = js.job_id " +
            "INNER JOIN services_types ON js.service_type_id = services_types.service_type_id " +
            "WHERE u.user_id = ?1",
            nativeQuery = true)
    List<Object[]> findJobDetailsByUserId(Integer userId);

    @Query(value = "SELECT tj.name_type_job, " +
            "services_types.name_service_type, " +
            "status_jobs.name_status_job, " +
            "j.job_id, " +
            "v.model, " +
            "v.color, " +
            "u.name, " +
            "u.last_name, " +
            "j.created_at " +
            "FROM jobs j " +
            "INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id " +
            "INNER JOIN clients c ON v.client_id = c.client_id " +
            "INNER JOIN users u ON c.user_id = u.user_id " +
            "INNER JOIN status_jobs ON j.status_job_id = status_jobs.status_job_id " +
            "INNER JOIN type_jobs tj ON j.type_job_id = tj.type_job_id " +
            "INNER JOIN jobs_services js ON j.job_id = js.job_id " +
            "INNER JOIN services_types ON js.service_type_id = services_types.service_type_id " +
            "WHERE u.user_id = :userId",
            nativeQuery = true)
    List<Object[]> findJobDetailsByUserIdNamed(@Param("userId") Integer userId);

    @Query(value =
            "SELECT tj.name_type_job, " +
                    "services_types.name_service_type, " +
                    "status_jobs.name_status_job, " +
                    "j.job_id, " +
                    "v.model, " +
                    "v.color, " +
                    "u.name, " +
                    "u.last_name, " +
                    "j.created_at " +
                    "FROM jobs j " +
                    "INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id " +
                    "INNER JOIN clients c ON v.client_id = c.client_id " +
                    "INNER JOIN users u ON c.user_id = u.user_id " +
                    "INNER JOIN status_jobs ON j.status_job_id = status_jobs.status_job_id " +
                    "INNER JOIN type_jobs tj ON j.type_job_id = tj.type_job_id " +
                    "INNER JOIN jobs_services js ON j.job_id = js.job_id " +
                    "INNER JOIN services_types ON js.service_type_id = services_types.service_type_id " +
                    "WHERE u.user_id = :userId AND j.status_job_id = :statusId",
            nativeQuery = true)
    List<Object[]> findJobDetailsByUserAndStatus(@Param("userId") Integer userId,
                                                 @Param("statusId") Integer statusId);

}
