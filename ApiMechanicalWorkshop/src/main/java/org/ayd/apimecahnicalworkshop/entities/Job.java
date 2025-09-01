package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@Data @NoArgsConstructor @AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "started_at")
    private String startedAt;

    @Column(name = "finished_at")
    private String finishedAt;

    @ManyToOne
    @JoinColumn(name = "type_job_id")
    private TypeJob typeJob;

    @ManyToOne
    @JoinColumn(name = "status_job_id")
    private StatusJob statusJob;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_hours", columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
    private Double estimatedHours;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
}
