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
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id", nullable = false)
    private Long serviceTypeId;
    @Column(name = "name_service_type", nullable = false, length = 100)
    private String nameServiceType;
    @Column(name = "description_service_type", nullable = false, length = 200)
    private String descriptionServiceType;
    @Column(name = "base_price", nullable = false)
    private Double basePrice;
}
