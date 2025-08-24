package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs_parts")
@Data @NoArgsConstructor @AllArgsConstructor
public class JobParts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_part_id", nullable = false)
    private Long jobPartId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "part_id", nullable = false)
    private Long partId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double price;

    @Column(name = "created_at", nullable = false)
    private String createdAt;
}
