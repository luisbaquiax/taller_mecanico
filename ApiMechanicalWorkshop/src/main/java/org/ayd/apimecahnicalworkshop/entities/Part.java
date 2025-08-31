package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long partId;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "name_part", nullable = false, length = 100)
    private String namePart;

    @Column(name = "brand_part", nullable = false, length = 100)
    private String brandPart;

    @Column(name = "description_part", nullable = false, length = 200)
    private String descriptionPart;

    @Column(name = "cost_price", nullable = false)
    private Double costPrice;

    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @Column(name = "stock_part", nullable = false)
    private int stockPart;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    //private String createdAt;
    //private String updatedAt;
}
