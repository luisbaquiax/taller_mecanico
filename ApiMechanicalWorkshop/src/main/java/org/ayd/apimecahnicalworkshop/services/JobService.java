package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.DetailJobDTO;
import org.ayd.apimecahnicalworkshop.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<DetailJobDTO> getAllJobDetails() {
        List<Object[]> results = jobRepository.findAllJobDetails();
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

    public List<DetailJobDTO> getJobDetailsByUserId(Integer userId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserId(userId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }
    public List<DetailJobDTO> getJobDetailsByUserIdNamed(Integer userId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserIdNamed(userId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

    public List<DetailJobDTO> getJobDetailsByUserAndStatus(Integer userId, Integer statusId) {
        List<Object[]> results = jobRepository.findJobDetailsByUserAndStatus(userId, statusId);
        return results.stream().map(row -> new DetailJobDTO(
                (String) row[0],    // name_type_job
                (String) row[1],    // name_service_type
                (String) row[2],    // name_status_job
                (Integer) row[3],   // job_id
                (String) row[4],    // model
                (String) row[5],    // color
                (String) row[6],    // name
                (String) row[7],    // last_name
                ((Timestamp) row[8]).toLocalDateTime() // created_at
        )).collect(Collectors.toList());
    }

}
