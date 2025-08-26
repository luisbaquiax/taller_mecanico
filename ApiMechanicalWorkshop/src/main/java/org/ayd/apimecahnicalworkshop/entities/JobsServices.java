package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs_services")
@Data @NoArgsConstructor @AllArgsConstructor
public class JobsServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_service_id", nullable = false)
    private Long jobServiceId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "service_type_id", nullable = false)
    private Long serviceTypeId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double price;
}
