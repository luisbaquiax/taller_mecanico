package org.ayd.apimecahnicalworkshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "total", nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Double total;

    @Column(name = "invoice_status", columnDefinition = "ENUM('pendiente','pagada','anulada') DEFAULT 'pendiente'")
    private String invoiceStatus;

    @Column(name = "issued_at", nullable = false)
    private String issuedAt;
}
