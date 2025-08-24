package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "password_reset")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reset_id")
    private Integer resetId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Entidad User ya existente

    @Column(name = "token", nullable = false, length = 255)
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "used", nullable = false)
    private Boolean used = false;

}