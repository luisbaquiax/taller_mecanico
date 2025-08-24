package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "quotation_status", nullable = false)
    private String quotationStatus;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "valid_until")
    private String validUntil;

}
