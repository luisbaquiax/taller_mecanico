package org.ayd.apimecahnicalworkshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "updates_jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data @NoArgsConstructor @AllArgsConstructor
public class UpdateJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_job_id", nullable = false)
    private Long updateJobId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "status_update_job_id", nullable = false)
    private Long statusUpdateJobId;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "hours_spent", columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
    private Double hoursSpent;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

}
