package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    /**
     * Obtiene todas las facturas del usuario asociadas con sus vehículos
     */
    @Query(value = """
        SELECT 
            i.invoice_id as invoiceId,
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            i.total as total,
            i.invoince_status as invoiceStatus,
            i.inssued_at as issuedAt,
            j.description as jobDescription
        FROM invoices i
        INNER JOIN jobs j ON i.job_id = j.job_id
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        WHERE c.user_id = :userId
        ORDER BY i.inssued_at DESC
        """, nativeQuery = true)
    List<Object[]> findInvoiceByClientId(@Param("userId") Long userId);

    /**
     * Obtiene facturas por usuario filtrado por estado
     */
    @Query(value = """
        SELECT 
            i.invoice_id as invoiceId,
            j.job_id as jobId,
            v.licence_plate as licencePlate,
            v.brand as vehicleBrand,
            v.model as vehicleModel,
            v.year as vehicleYear,
            i.total as total,
            i.invoince_status as invoiceStatus,
            i.inssued_at as issuedAt,
            j.description as jobDescription
        FROM invoices i
        INNER JOIN jobs j ON i.job_id = j.job_id
        INNER JOIN vehicles v ON j.vehicle_id = v.vehicle_id
        INNER JOIN clients c ON v.client_id = c.client_id
        WHERE c.user_id = :userId AND i.invoince_status = :status
        ORDER BY i.inssued_at DESC
        """, nativeQuery = true)
    List<Object[]> findInvoiceByStatus(@Param("userId") Long userId, @Param("status") String status);


}
