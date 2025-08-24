package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_item_id", nullable = false)
    private Long quotationItemId;

    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
}
