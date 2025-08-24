package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id", nullable = false)
    private Long serviceTypeId;
    private String nameServiceType;
    private String descriptionServiceType;
    private Double basePrice;
}
