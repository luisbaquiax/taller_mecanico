package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends CrudRepository<Part, Long> {


    @Query(value = """
        SELECT 
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            p.name_part as partName,
            p.brand_part as partBrand,
            jp.quantity as quantity,
            jp.price as price,
            jp.created_at as createdAt,
            j.description as jobDescription
        FROM jobs j
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        INNER JOIN jobs_parts jp ON j.job_id = jp.job_id
        INNER JOIN parts p ON jp.part_id = p.part_id
        WHERE c.user_id = :userId
        ORDER BY jp.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findPartByUserID(@Param("userId") Long userId);

    /**
     * Obtiene repuestos por usuario filtrado por vehículo específico
     */
    @Query(value = """
        SELECT 
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            p.name_part as partName,
            p.brand_part as partBrand,
            jp.quantity as quantity,
            jp.price as price,
            jp.created_at as createdAt,
            j.description as jobDescription
        FROM jobs j
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        INNER JOIN jobs_parts jp ON j.job_id = jp.job_id
        INNER JOIN parts p ON jp.part_id = p.part_id
        WHERE c.user_id = :userId AND v.vehicle_id = :vehicleId
        ORDER BY jp.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findPartByVehicleID(@Param("userId") Long userId, @Param("vehicleId") Long vehicleId);

}
