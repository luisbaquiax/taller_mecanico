package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor @AllArgsConstructor
public class StatusUpdateJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_update_job_id", nullable = false)
    private Long statusUpdateJobId;

    @Column(name = "name_status_update_job", nullable = false, length = 100)
    private String nameStatusUpdateJob;

    @Column(name = "description_status_update_job", nullable = false, length = 200)
    private String descriptionStatusUpdateJob;
}
