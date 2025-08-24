package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_job_id", nullable = false)
    private Long typeJobId;

    @Column(name = "name_type_job", nullable = false, length = 100)
    private String nameTypeJob;

    @Column(name = "description_type_job", nullable = false, length = 200)
    private String descriptionTypeJob;
}
