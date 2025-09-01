package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Quotation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuotationRepository extends CrudRepository<Quotation, Long> {


    @Query(value = """
        SELECT 
            q.quotation_id as quotationId,
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            q.total as total,
            q.quotation_status as quotationStatus,
            q.created_at as createdAt,
            q.valid_until as validUntil,
            j.description as jobDescription
        FROM quotations q
        INNER JOIN jobs j ON q.job_id = j.job_id
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        WHERE c.user_id = :userId
        ORDER BY q.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findQuotationByClientId(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            q.quotation_id as quotationId,
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            q.total as total,
            q.quotation_status as quotationStatus,
            q.created_at as createdAt,
            q.valid_until as validUntil,
            j.description as jobDescription
        FROM quotations q
        INNER JOIN jobs j ON q.job_id = j.job_id
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        WHERE c.user_id = :userId AND q.quotation_status = :status
        ORDER BY q.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findBystate(@Param("userId") Long userId, @Param("status") String status);

    @Query(value = """
        SELECT 
            q.quotation_id as quotationId,
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            q.total as total,
            q.quotation_status as quotationStatus,
            q.created_at as createdAt,
            q.valid_until as validUntil,
            j.description as jobDescription
        FROM quotations q
        INNER JOIN jobs j ON q.job_id = j.job_id
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        WHERE c.user_id = :userId 
        AND (q.valid_until IS NULL OR q.valid_until > NOW())
        ORDER BY q.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findQuotationsByValid(@Param("userId") Long userId);


}
