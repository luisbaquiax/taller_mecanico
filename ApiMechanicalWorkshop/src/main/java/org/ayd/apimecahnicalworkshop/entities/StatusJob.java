package org.ayd.apimecahnicalworkshop.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status_jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data @AllArgsConstructor @NoArgsConstructor
public class StatusJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_job_id", nullable = false)
    private Long statusJobId;

    @Column(name = "name_status_job", nullable = false, length = 100)
    private String nameStatusJob;

    @Column(name = "description_status_job", nullable = false, length = 200)
    private String descriptionStatusJob;

}
