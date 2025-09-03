package org.ayd.apimecahnicalworkshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_two_factor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeMethodTwoFactor {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "type_two_factor_id", nullable = false)
    private Long typeTwoFactorId;

    @Column(name = "name_type_two_factor", nullable = false, length = 200)
    private String name;

    @Column(name = "description_type_two_factor", nullable = false, length = 200)
    private String description;
}
