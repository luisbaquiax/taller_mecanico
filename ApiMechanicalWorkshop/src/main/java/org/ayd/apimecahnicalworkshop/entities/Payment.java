package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    /**
     * -- payments
     * CREATE TABLE payments(
     *     payment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     *     invoice_id INT NOT NULL,
     *     payment_method ENUM('efectivo','tarjeta','transferencia') DEFAULT 'efectivo',
     *     amount DECIMAL(10,2) NOT NULL,
     *     payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
     *     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
     *     FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
     * );
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

    @Column(name = "payment_method", columnDefinition = "ENUM('efectivo','tarjeta','transferencia') DEFAULT 'efectivo'")
    private String paymentMethod;

    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double amount;

    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

}
