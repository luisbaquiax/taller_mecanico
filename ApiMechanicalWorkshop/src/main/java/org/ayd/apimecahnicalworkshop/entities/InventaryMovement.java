package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventary_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventaryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mv_id", nullable = false)
    private Long mvId;

    @Column(name = "part_id", nullable = false)
    private Long partId;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "type_movement", nullable = false)
    private String typeMovement;

    @Column(name = "reference", nullable = false, length = 255)
    private String reference;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

}
